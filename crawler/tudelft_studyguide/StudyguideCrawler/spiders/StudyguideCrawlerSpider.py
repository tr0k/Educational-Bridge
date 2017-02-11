from scrapy.item import Item, Field
from selenium import webdriver
from scrapy.spiders import Spider
import scrapy
import logging
import time
import string


class StudyguideCrawlerSpider(Spider):
    name = "StudyguideCrawlerSpider"
    allowed_domains = ["www.studiegids.tudelft.nl"]
    course_url_template = 'http://www.studiegids.tudelft.nl/a101_displayCourse.do?course_id=%s&&SIS_SwitchLang=en'

    def __init__(self, category=None, *args, **kwargs):
        self.driver = webdriver.Firefox()
        super(StudyguideCrawlerSpider, self).__init__(*args, **kwargs)

        self.start_urls = ['http://www.studiegids.tudelft.nl/menuAction.do?toolbarSelection=tree']
        # self.start_urls = ['http://www.studiegids.tudelft.nl/a101_displayCourse.do?course_id=36830']

        logging.log(logging.INFO, "Init finished")

    # remove special characters
    def rem_spec_char(self, str):
        out = str
        out = out.replace( u'\u2018', u"'")
        out = out.replace( u'\u2019', u"'")
        out = out.replace( u'\u201c', u"'")
        out = out.replace( u'\u201d', u"'")
        out = out.replace( u'"', u"'")
        printable = set(string.printable)
        out = filter(lambda x: x in printable, out)
        return out

    # parse course page and extract data
    def parse_course_page(self, url):
        logging.log(logging.INFO, "starting to parse divs")

        # parse course info
        title = self.driver.find_element_by_css_selector(
            'form[name="course"] table.sisBody table tr:nth-of-type(2) table td:nth-of-type(2)').text
        cid = self.driver.find_element_by_css_selector(
            'form[name="course"] table.sisBody table tr:nth-of-type(2) table td:nth-of-type(1)').text
        contents = self.driver.find_element_by_xpath(
            '//td[contains(., "Course Contents")]/following-sibling::td/div[@id="freeText"]').text
        goals = self.driver.find_element_by_xpath(
            '//td[contains(., "Study Goals")]/following-sibling::td/div[@id="freeText"]').text

        items = []

        item = self.CourseItem()
        item['course_id'] = cid
        item['course_title'] = self.rem_spec_char(title)
        item['course_contents'] = self.rem_spec_char(contents)
        item['course_study_goals'] = self.rem_spec_char(goals)
        item['course_url'] = url

        items.append(item)

        logging.info("write parsed item %s to file" % url)
        self.write_item_to_file(item)

        return items

    def write_item_to_file(self, item):
        with open("courses.csv", "a") as myfile:
            myfile.write("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n" % (
                item['course_id'], item['course_title'], item['course_contents'], item['course_study_goals'],
                item['course_url']))

    def parse(self, response):
        logging.log(logging.INFO, "starting to parse site with selenium")
        self.driver.get(response.url)

        time.sleep(2)

        if 'displayCourse.do' in response.url:
            yield self.parse_course_page(response.url)
        else:
            urls = []

            # loop over all organisations
            org_number = len(self.driver.find_elements_by_css_selector('select[name="bureau_id"] option'))
            for org in range(2, org_number + 1):
                self.driver.find_element_by_css_selector('select[name="bureau_id"] option:nth-of-type(%d)' % org).click()
                time.sleep(2)

                # loop over all education types
                type_number = len(self.driver.find_elements_by_css_selector('select[name="educationtype_id"] option'))
                for typ in range(2, type_number + 1):
                    self.driver.find_element_by_css_selector('select[name="educationtype_id"] option:nth-of-type(%d)' % typ).click()
                    time.sleep(2)

                    # loop over all educations
                    edu_number = len(self.driver.find_elements_by_css_selector('select[name="education_id"] option'))
                    for edu in range(2, edu_number + 1):
                        self.driver.find_element_by_css_selector('select[name="education_id"] option:nth-of-type(%d)' % edu).click()
                        time.sleep(2)

                        logging.info("education nr %d" % edu)

                        # switch to english if not already done

                        # expand tree
                        try:
                            self.driver.find_element_by_link_text('open all').click()
                            time.sleep(2)

                            # run over all node links
                            for link in self.driver.find_elements_by_css_selector('div.dtree a.node[href^="javascript:_c("]'):
                                link_url = link.get_attribute('href')
                                itemid = link_url.replace('javascript:_c(', '').replace(');','')
                                urls.append(self.course_url_template % itemid)
                        except:
                            logging.info("no courses for this combination")

            # loop over urls and create requests
            for url in urls:
                yield scrapy.Request(url, callback=self.parse)

        yield None


    class CourseItem(Item):
        course_id = Field()
        course_title = Field()
        course_contents = Field()
        course_study_goals = Field()
        course_url = Field()
