import json
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup

r = open('members.txt', 'r')
members = []
while True:
    line = r.readline().rstrip()
    if not line:
        break
    members.append(line)
r.close()

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

f = open('GwangjuClass01Git.csv', 'w')

url = "http://lab.ssafy.com/"

line = ''

page = browser.page_source
soup = BeautifulSoup(page, 'html.parser')

for member in members:
    browser.get(url + member)
    elem = WebDriverWait(browser, 10).until(EC.presence_of_element_located(
        (By.XPATH, "//*[@id='js-overview']/div[1]/div/div/div[1]/div")))
    page = browser.page_source
    soup = BeautifulSoup(page, 'html.parser')
    jandis = soup.find_all("rect")
    line += member + ', '
    for jandi in jandis[:-5]:
        contributions = jandi["data-original-title"].split('<br />')[
            0].split()[0]
        if contributions == 'No':
            contributions = '0'
        line += contributions + ', '
    line += '\n'
f.write(line)
browser.quit()
