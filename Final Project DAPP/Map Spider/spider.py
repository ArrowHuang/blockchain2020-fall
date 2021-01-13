import requests
import time
import csv
import pandas as pd
from selenium import webdriver
from selenium.webdriver.support.ui import Select
from bs4 import BeautifulSoup
options = webdriver.ChromeOptions()
options.add_argument("headless")


def get_coordinate(lat, log):
    chromedriver = '/Users/arrow/Desktop/block final/chromedriver_win32/chromedriver' # ChromeDriver
    browser = webdriver.Chrome(chromedriver) 
    
    # 利用selenium模擬用戶輸入經緯度座標得到縣市資訊
    browser.get("http://www.map.com.tw/")
    search = browser.find_element_by_id("searchWord")
    search.clear()
    search.send_keys(lat+','+log)
    browser.find_element_by_xpath("/html/body/form/div[10]/div[2]/img[2]").click() # 模擬按下搜尋鍵
    time.sleep(2)
    iframe = browser.find_elements_by_tag_name("iframe")[1]
    browser.switch_to.frame(iframe)
    coor_btn = browser.find_element_by_xpath("/html/body/form/div[4]/table/tbody/tr[3]/td/table/tbody/tr/td[2]")  # 縣市區資訊對應路徑
    coor_btn.click()
    coor = browser.find_element_by_xpath("/html/body/form/div[5]/table/tbody/tr[1]/td")
    coor = coor.text.strip().split('\n')[0]
    browser.quit()
    return coor.replace(' ','') 

candidate = ["臺北","台北", "新北", "桃園","台中", "臺中","台南", "臺南", "高雄", "基隆", "新竹", "嘉義", "苗栗", "彰化", "南投", "雲林", "嘉義", "屏東", "宜蘭", "花蓮","台東", "臺東", "澎湖","馬祖"]
location_strict = []
data = pd.read_csv('result_3.csv')

for index, row in data.iterrows():
    try:
        resultFile3 = open('new_result_3.csv', 'a+', newline='',encoding='utf-8-sig')
        writer = csv.writer(resultFile3)
        if(index==0):
            writer.writerow(['oid','gps','location'])

        res = []
        oid = row['oid']
        location = row['gps']
        location_list = list(set(location.split('),(')))
        location_strict_sub = ""
        for l in location_list:
            new_l = l.replace('(','').replace(')','')
            lat = new_l.split(',')[0].strip()
            log = new_l.split(',')[1].strip()
            local_place = get_coordinate(lat,log)
            # print(local_place)
            if(local_place[:2] in candidate):
                location_strict_sub = local_place[:3]
                break

        res.append(oid)
        res.append(','.join(location_list))
        res.append(location_strict_sub)

        writer.writerow(res)
        resultFile3.close()
    except:
        pass