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
import datetime


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

def get_events(id):

    today = datetime.date.today()
    before = today + datetime.timedelta(days=1)
    headers = {
        'Content-Type': 'application/json; charset=utf-8',
        'PRIVATE-TOKEN' : private_token,
    }

    params = {
        'simple' : 'true',
        'per_page' : '100',
        'before' : before,
    }

    contributions = get_req("".join(['/users/', str(id), '/events']), headers, params)

    content = {}
    for cont in contributions:
        try : 
            date = cont['created_at'].split('T')[0]
        except AttributeError :
            continue
        
        try: 
            n = content[date]
        except KeyError :
            n = 0
        finally :
            content[date] = n + 1

    res = []

    n = 0;
    while True:
        key_date = datetime.date(2021,1,1) + datetime.timedelta(days=n)
        n+=1
        if key_date > datetime.date(today.year, today.month, today.day) : break

        try :
            value = content[str(key_date)]
        except KeyError :
            value = 0
        finally :
            res.append(value)

    # print(datetime.date(2021,1,1) + datetime.timedelta(days=10))
    ## timedelta(days=number) 로 n일 뒤 날짜 구할 수 있음
    
    return res

def save_data(team_code, data):
    csv_file_name = 'user_info.csv'
    json_file_name = '../frontend/src/contributions.json'
    csv_content = {}
    cols = []
    ## read csv_content
    try:
        f = open(csv_file_name,'r', encoding='utf-8-sig')
        line = f.readline()
        cols = line.split(',')
        while True:
            line = f.readline()
            if not line: break

            line_d = line.split(',')
            line_c = {}
            for i in range(0,len(cols)):
                line_c[cols[i]] = line_d[i]

            csv_content[int(line_d[0])] = line_c

    except IOError:
        f = open(csv_file_name, 'w', encoding='utf-8-sig')
    finally:
        f.close()

    json_students = []

    try:
        f = open(json_file_name, 'r', encoding='utf-8-sig')
        json_data = json.load(f)
        json_students = json_data['students']
    except IOError:
        f = open(csv_file_name, 'w', encoding='utf-8-sig')
    finally:
        f.close()

    json_content = {
        'update_time' : str(datetime.datetime.now()),
        'students' : json_students,
    }


    for user in data:
        user['no'] = user['id']
        user['id'] = user['username']
        user['region'] = team_code[6]

       

        for i in range(1,4):
            try:
                user[''.join(['team',str(i)])] = csv_content[user['no']][''.join(['team',str(i)])]
            except KeyError:
                user[''.join(['team',str(i)])] = ' '

        user[''.join(['team',team_code[4]])] = team_code[7:10]
        user['\n'] = '\n'
        csv_content[user['no']] = user

        ## set json data
        st_data = {
            'name' : user['name'],
            'git_id' : user['id'],
            'commits' : get_events(user['no']),
            'region' : user['region'],
            'class' : [user['team1'], user['team2'], user['team3']],
        }

        json_content['students'].append(st_data)

    ## write data
    f = open(csv_file_name, 'w', encoding='utf-8-sig')
    f.write("no,name,id,region,team1,team2,team3,\n")

    for key in csv_content.keys():
        n_data = csv_content[key]
        n_line = []
        for col in cols:
            n_line.append(str(n_data[col]))
        f.write(",".join(n_line))

    f.close()

    f = open(json_file_name, 'w', encoding='utf-8-sig')
    f.write(json.dumps(json_content, ensure_ascii=False))
    f.close()



def main():
    search = "S04P22"

    if len(sys.argv) > 1 : search = sys.argv[1] 
    projects = get_projects(search)

    n = 1
    for project in projects :
        print("".join([str(n), 'team recording....']))
        n+=1
        users = get_users(str(project['id']))
        save_data(project['path'], users)
     
def set_env():
    global base_url
    global private_token
    base_url = "https://lab.ssafy.com/api/v4";
    private_token = "a3sroJgsz_iuJxeT9-Mj"

if __name__ == "__main__":
    set_env()
    print("".join(['start time : ', str(datetime.datetime.now())]))
    main()
    print("".join(['end time : ', str(datetime.datetime.now())]))
    # get_events(2350)