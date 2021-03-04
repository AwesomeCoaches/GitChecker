# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
import json
import requests
import time

# r = open('members.txt', 'r')
# while True:
#     line = r.readline().rstrip()
#     if not line:
#         break
#     members.append(line)
# r.close()

members = []
res = requests.get('http://t4coach44.p.ssafy.io/api/students/')
res.raise_for_status()
for student in res.json():
    members.append([student['username'], student['name']])
secrets = json.loads(open('secrets.json').read())
COACH_ID = secrets["COACH_ID"]
COACH_PASSWORD = secrets["COACH_PASSWORD"]

options = webdriver.ChromeOptions()
options.headless = True
options.add_argument(
    "user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
browser = webdriver.Chrome('./chromedriver', options=options)

browser.get("https://project.ssafy.com/")

idForm = browser.find_element_by_id('userId')
idForm.send_keys(COACH_ID)
pwdForm = browser.find_element_by_id('userPwd')
pwdForm.send_keys(COACH_PASSWORD)
browser.find_element_by_link_text('로그인').click()

f = open('contributions.csv', 'w', encoding='utf-8-sig')

url = "http://lab.ssafy.com/"

tm = time.localtime(time.time())
line = time.strftime('%Y-%m-%d %I:%M:%S %p', tm) + '\n'
line += 'ID, NAME\n'
page = browser.page_source
soup = BeautifulSoup(page, 'html.parser')

for member, name in members[:50]:
    print(member, name)
    browser.get(url + member)
    try:
        elem = WebDriverWait(browser, 10).until(EC.presence_of_element_located(
            (By.XPATH, "//*[@id='js-overview']/div[1]/div/div/div[1]/div")))
    except:
        continue
    page = browser.page_source
    soup = BeautifulSoup(page, 'html.parser')
    jandis = soup.find_all("rect")
    line += member + ', ' + name + ', '
    for jandi in jandis[:-5]:
        contributions = jandi["data-original-title"].split('<br />')[
            0].split()[0]
        if contributions == 'No':
            contributions = '0'
        line += contributions + ', '
    line += '\n'
f.write(line)
browser.quit()
