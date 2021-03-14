# -*- coding: utf-8 -*-

from bs4 import BeautifulSoup
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium import webdriver
from random import *
import json
import requests
import time
import sys

global base_url 
global private_token

def get_req(url, headers, params):
    URL = base_url + url

    res = requests.get(base_url + url, headers=headers, params=params)

    res.raise_for_status()

    return res.json()

def get_projects(search):

    headers = {
        'Content-Type': 'application/json; charset=utf-8',
        'PRIVATE-TOKEN' : private_token,
    }

    params = {
        'simple' : 'true',
        'per_page' : '100',
        'search' : search,
    }

    return get_req("/projects", headers, params)

def get_users(project_id):
    headers = {
        'Content-Type': 'application/json; charset=utf-8',
        'PRIVATE-TOKEN' : private_token,
    }

    params = {
        'simple' : 'true',
        'per_page' : '100',
    }

    url_splited = [ '/projects/', project_id, '/members']

    return get_req("".join(url_splited), headers, params)

def save_csv(team_code, data):
    file_name = 'user_info.csv'
    content = {}
    cols = []
    ## read content
    try:
        f = open(file_name,'r', encoding='utf-8-sig')
        line = f.readline()
        cols = line.split(',')
        while True:
            line = f.readline()
            if not line: break

            line_d = line.split(',')
            line_c = {}
            for i in range(0,len(cols)):
                line_c[cols[i]] = line_d[i]

            content[int(line_d[0])] = line_c

    except IOError:
        f = open(file_name, 'w', encoding='utf-8-sig')
    finally:
        f.close()

    ## write data
    f = open(file_name, 'w', encoding='utf-8-sig')
    f.write("no,name,id,region,team1,team2,team3,\n")

    for user in data:
        user['no'] = user['id']
        user['id'] = user['username']
        user['region'] = team_code[6]
       
        for i in range(1,4):
            try:
                user[''.join(['team',str(i)])] = content[user['no']][''.join(['team',str(i)])]
            except KeyError:
                user[''.join(['team',str(i)])] = ' '

        user[''.join(['team',team_code[4]])] = team_code[7:10]
        user['\n'] = '\n'
        content[user['no']] = user

    for key in content.keys():
        n_data = content[key]
        n_line = []
        for col in cols:
            n_line.append(str(n_data[col]))
        f.write(",".join(n_line))

    f.close()



def main():
    global base_url
    global private_token
    base_url = "https://lab.ssafy.com/api/v4";
    private_token = "a3sroJgsz_iuJxeT9-Mj"

    search = "S04P"

    if len(sys.argv) > 1 : search = sys.argv[1] 
    projects = get_projects(search)

    for project in projects :
        print("*" * randint(1,30))
        users = get_users(str(project['id']))
        save_csv(project['path'], users)
     
        

if __name__ == "__main__":
    main()