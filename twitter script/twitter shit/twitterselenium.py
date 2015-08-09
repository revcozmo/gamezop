from selenium import webdriver
from time import sleep
import time
import os
from time import strftime
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
with open('usernames.txt') as f:
    usernames = f.readlines()
print usernames
TIME = 0
service_args = [
    '--proxy=proxy62.iitd.ernet.in:3128',
    '--proxy-auth=bb5130028:FUCKTHEWORLD2555'
    ]
driver = webdriver.Firefox()
#f = open('ale_fo.txt','w')
#driver = webdriver.PhantomJS("./phantomjs/bin/phantomjs.exe", service_args=service_args)
for i in range(0,len(usernames)):
    bakchod = usernames[i]
    if not os.path.isfile(bakchod[:-1]+".txt"):
        #print bakchod[:-1]
        #print "-"*50
        driver.quit()
        sleep(10)
        text_file = open(bakchod[:-1]+".txt","w")
        #driver = webdriver.PhantomJS("./phantomjs/bin/phantomjs.exe", service_args=service_args)
        driver = webdriver.Firefox()
        driver.maximize_window()
        #driver.get("https://twitter.com/alexia/following")
        driver.get("https://twitter.com")
        #driver.add_cookie({'name': 'foo', 'value': 'bar'})
        #sleep(8)
        wait = WebDriverWait(driver, 10)
        #email_field = driver.find_element_by_class_name("js-username-field email-input js-initial-focus")
        #email_field = driver.find_element_by_css_selector(".username field > #signin-email")
        email_field = driver.find_element_by_xpath('//*[@class="text-input email-input"]')
        email_field.send_keys("yashash_agarwal@rediff.com")

        password_field = driver.find_element_by_xpath('//*[@class="text-input flex-table-input"]')
        password_field.send_keys("temporary")
        sleep(3)
        password_field.send_keys(Keys.RETURN)
        sleep(5)
        body = driver.find_element_by_tag_name("body")
        body.send_keys(Keys.CONTROL + 't')
        try:
            driver.get("https://twitter.com/"+bakchod[:-1]+"/following")
            count = 0
            startTime = strftime("%H:%M:%S")
            st = time.time()
            while count < 1000:
                count += 1
                elem = driver.find_element_by_class_name('ProfileNameTruncated')                    
                if count == 1:
                    sleep(5)
                elem.send_keys(Keys.PAGE_DOWN)
                if count%10 == 0:
                    sleep(0.1)
            Listlink = driver.find_elements_by_xpath('//div[@class="ProfileCard-content"]//div[@class="ProfileCard-userFields"]//div[@class="ProfileNameTruncated"]//div[@class="u-textTruncate u-inlineBlock"]//a[@href]')
            for element in Listlink:
                text_file.write(element.get_attribute('href')[20:]+"\n")
            text_file.close()
            #driver.quit()
            endTime = strftime("%H:%M:%S")
            et = time.time()
            t = et-st
            print bakchod[:-1],"-"*10,startTime,"-"*10,endTime
            print bakchod[:-1],"-"*30,t
            TIME += t/60
            print "TIME =",TIME
            print "-"*70
            #sleep(20)
            #TIME += 20/60
        except:
            #body.send_keys(Keys.CONTROL + 'w')
            #driver.quit()
            #sleep(20)
            #TIME += 20/60
            print "-"*20,"PASS","-"*20
            pass
    else:
        print "-"*30+bakchod[:-1]+"-"*30
