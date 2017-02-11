from scrapy.item import Item, Field
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from scrapy.spiders import Spider
import scrapy
import requests
import logging
import time
import string


class SeleniumCrawlerSpider(Spider):
    name = "SeleniumCrawlerSpider"
    allowed_domains = ["coursera.org"]
    blacklist = ['/supplement/', '/exam/', '/programming/', '/discussionPrompt/']

    def __init__(self, category=None, *args, **kwargs):
        self.driver = webdriver.Firefox()
        super(SeleniumCrawlerSpider, self).__init__(*args, **kwargs)

        # self.start_urls = ['https://www.coursera.org/learn/android-app']
        # self.start_urls = ['https://www.coursera.org/learn/addiction-and-the-brain/lecture/AHCtG/course-overview']
        # self.start_urls = ['https://www.coursera.org/browse']
        # self.start_urls = ['https://www.coursera.org/browse/computer-science/software-development']
        # self.start_urls = ['https://www.coursera.org/learn/android-app', 'https://www.coursera.org/learn/python-data', 'https://www.coursera.org/learn/object-oriented-java']
        self.start_urls = [
            # 'https://www.coursera.org/browse/computer-science/software-development?languages=en&_facet_changed_=true',
            # 'https://www.coursera.org/browse/computer-science/algorithms?languages=en&_facet_changed_=true',
            'https://www.coursera.org/browse/physical-science-and-engineering/electrical-engineering?languages=en&_facet_changed_=true',
            'https://www.coursera.org/browse/physical-science-and-engineering/mechanical-engineering?languages=en&_facet_changed_=true',
            'https://www.coursera.org/browse/physical-science-and-engineering/environmental-science-and-sustainability?languages=en&_facet_changed_=true',
            # 'https://www.coursera.org/browse/computer-science/mobile-and-web-development?languages=en&_facet_changed_=true',
            # 'https://www.coursera.org/browse/computer-science/computer-security-and-networks?languages=en&_facet_changed_=true',
            # 'https://www.coursera.org/browse/computer-science/design-and-product?languages=en&_facet_changed_=true',
            # 'https://www.coursera.org/browse/data-science/data-analysis?languages=en&_facet_changed_=true',
            # 'https://www.coursera.org/browse/data-science/machine-learning?languages=en&_facet_changed_=true',
            # 'https://www.coursera.org/browse/data-science/probability-and-statistics?languages=en&_facet_changed_=true'
        ]

        logging.log(logging.INFO, "Init finished")

    # remove special characters
    def rem_spec_char(self, str):
        out = str
        out = out.replace(u'\u2018', u"'")
        out = out.replace(u'\u2019', u"'")
        out = out.replace(u'\u201c', u"'")
        out = out.replace(u'\u201d', u"'")
        out = out.replace(u'"', u"'")
        out = out.replace('\n', ' ')
        printable = set(string.printable)
        out = filter(lambda x: x in printable, out)
        return out

    # parse video page and extract data
    def parse_video_items(self, url, meta):
        logging.log(logging.INFO, "starting to parse divs")

        title = self.driver.find_element_by_css_selector(".c-video-title h1").text
        # desc = self.driver.find_elements_by_class_name("c-transcript")[0].text
        try:
            transcript_link = self.driver.find_element_by_css_selector('a[download="transcript.txt"]').get_attribute(
                "href")

            # download transcript
            f = requests.get(transcript_link)
            time.sleep(2)
            desc = f.text.replace('\n', ' ')
        except:
            desc = ''

        # course title is a bit hard to get TODO
        # course_tit = self.driver.find_element_by_css_selector("a[data-js-link-name='course_name']").text
        course_tit = ''

        items = []

        item = self.VideoItem()
        item['video_url'] = url
        item['video_title'] = self.rem_spec_char(title)
        item['video_description'] = self.rem_spec_char(desc)
        item['course_title'] = self.rem_spec_char(course_tit)

        items.append(item)

        logging.info("write parsed item %s to file" % url)
        self.write_item_to_file(item, meta)

        return items

    def parse_course_info(self):
        info = self.driver.find_element_by_css_selector('.rc-AboutBox.c-welcome-rounded-box').text
        info = self.rem_spec_char(info)
        return info

    def parse_course_title(self):
        info = self.driver.find_element_by_css_selector('.course-name').text
        info = self.rem_spec_char(info)
        return info

    def write_item_to_file(self, item, meta):
        with open("items.csv", "a") as myfile:
            myfile.write("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"\n" % (
                item['video_url'], item['video_title'], item['video_description'],
                meta['course_title'], meta['course_info'], meta['course_url']))

    def log_in(self, login_button):
        login_button.click()
        time.sleep(5)
        self.driver.find_element_by_css_selector('div.rc-LoginForm input[type="email"]').send_keys(
            'yapazitew@shotmail.ru')
        self.driver.find_element_by_css_selector('div.rc-LoginForm input[type="password"]').send_keys(
            'fVWRsrzxRhNRSr9N9lzF')
        self.driver.find_element_by_css_selector('div.rc-LoginForm button[data-js="submit"]').click()
        time.sleep(5)

    def log_in_video(self, login_button):
        logging.info("before login %s" % login_button)
        login_button.click()
        time.sleep(5)

        self.driver.find_element_by_css_selector('div[data-js="login-body"] input[name="email"]').send_keys(
            'yapazitew@shotmail.ru')
        self.driver.find_element_by_css_selector('div[data-js="login-body"] input[name="password"]').send_keys(
            'fVWRsrzxRhNRSr9N9lzF')
        self.driver.find_element_by_css_selector('div[data-js="login-body"] button[data-js="submit"]').click()
        time.sleep(10)

    def forward_to_home_and_lecture(self, response, href):
        req = scrapy.Request(href, callback=self.parse)

        try:
            self.driver.find_element_by_css_selector('h1.course-name')
            req.meta['course_info'] = self.parse_course_info()
            req.meta['course_title'] = self.parse_course_title()
            req.meta['course_url'] = response.url
        except NoSuchElementException:
            req.meta['course_info'] = response.meta['course_info']
            req.meta['course_title'] = response.meta['course_title']
            req.meta['course_url'] = response.meta['course_url']

        return req

    def forward_to_course_page(self, response, href):
        if '/browse/' in response.url:
            req = scrapy.Request(href, callback=self.parse)
            return req
        else:
            return None

    def forward_to_browse(self, response, href):
        print href
        return scrapy.Request(href, callback=self.parse)

    def parse(self, response):
        logging.log(logging.INFO, "starting to parse site with selenium")
        self.driver.get(response.url)
        time.sleep(5)

        # log in if not already done
        try:
            logging.info("try to log in")
            login = self.driver.find_element_by_css_selector('a[data-click-key="page.header.click.login"]')
            if login is not None:
                logging.info("log in attempt")
                self.log_in(login)
        except:
            logging.info("log in except 1")
            try:
                # login = self.driver.find_element_by_css_selector('.c-ph-right-nav-button ')
                login = self.driver.find_element_by_css_selector('a[data-reactid*=".1.0.0.0.2.0.1"]')
                if login is not None:
                    logging.info("log in attempt")
                    self.log_in_video(login)
            except:
                logging.info('already logged in')

        # distinguish between lecture pages and all other pages
        if '/lecture' in response.url:
            # branch for video pages
            logging.info('found ')
            yield self.parse_video_items(response.url, response.meta)
            # print items
            # self.driver.close()
        else:
            # branch for all other pages which lead to video pages
            for link in self.driver.find_elements_by_css_selector('a'):
                href = link.get_attribute('href')
                logging.info(href)

                if href is not None and ('/browse/' in href):
                    print ''
                    # yield self.forward_to_browse(response, href)
                elif href is not None and ('/learn/' in href and ('/home/' in href or '/lecture/' in href)):
                    yield self.forward_to_home_and_lecture(response, href)
                elif href is not None and ('/learn/' in href and not any(x in href for x in self.blacklist)):
                    yield self.forward_to_course_page(response, href)

        yield None

    class VideoItem(Item):
        video_title = Field()
        course_title = Field()
        video_url = Field()
        video_description = Field()
