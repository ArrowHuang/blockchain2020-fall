import re,os
import pandas as pd 
from bs4 import BeautifulSoup
import argparse
from tqdm import tqdm

paraser = argparse.ArgumentParser()
paraser.add_argument('path',help='display the data path to get data')
args = paraser.parse_args()

def mkdir(path):
    if(os.path.exists(path)):
        if(os.path.isdir(path)):
            print('{}/ already exists'.format(path))
        else:
            os.mkdir(path)
    else:
        os.mkdir(path)

def preprocess(path):
    oid_list = []
    Transaction = []
    Transaction_len = []
    procuct_name = []
    organization = []
    sell_date = []
    available_date = []
    product_num = []
    left_num = []
    gps_list = []

    file_list = os.listdir(path)
    for f in tqdm(file_list):
        oid = f.split('_')[1].split('.txt')[0].strip()
        oid_list.append(oid)

        with open(path+'/'+f,'r',encoding='utf-8') as f:
            content = f.read()
        f.close()

        soup = BeautifulSoup(content,'html.parser')
        info = soup.find('div',class_='text-vertical-center')
        info_tables = info.find_all('table')
        count = 1
        for it in info_tables:
            if(count==1):
                p_name = it.text.strip()
                procuct_name.append(p_name)
                count = count + 1
            elif(count==2):
                org = ''
                sd = ''
                ad = ''
                pn = ''
                ln = ''
                P_info = it.find('div',class_='username')
                P_infos = P_info.text.strip().split('\n')
                for p in P_infos:
                    if('產出組織' in p):
                        org = p.strip().split('：')[1].strip().replace('\t','')
                    elif('產出日期' in p):
                        sd = p.strip().split('：')[1].strip().replace('\t','')
                    elif('有效日期' in p):
                        ad = p.strip().split('：')[1].strip().replace('\t','')
                    elif('產出數量' in p):
                        pn = p.strip().split('：')[1].strip().replace('\t','')
                    elif('剩餘數量' in p):
                        ln = p.strip().split('：')[1].strip().replace('\t','')
                # print(org+' '+sd+' '+ad+' '+pn+' '+ln)
                organization.append(org)
                sell_date.append(sd)
                available_date.append(ad)
                product_num.append(pn)
                left_num.append(ln)

                count = count + 1
            elif(count==3):
                a_info = it.find_all('a')
                a_string = ''
                t_len = len(a_info)
                for a in range(t_len):
                    if(a < t_len-1):
                        a_string = a_string + a_info[a].text.strip() + ','
                    else:
                        a_string = a_string + a_info[a].text.strip()
                Transaction.append(a_string)
                Transaction_len.append(t_len)
                count = 1

        gps_inf = ''
        js_info = soup.find('script',type='text/javascript',src='')
        pattern = re.compile(r"lat: \d+\.?\d+, lng: \d+\.?\d+")
        if(js_info!=None):
            match = pattern.findall(js_info.text)
            if(match):
                tmp_list = []
                for m in match:
                    ymp_gps_inf = ''
                    lat = m.split(':')[1].split(',')[0].strip()
                    lon = m.split(':')[-1].strip()
                    ymp_gps_inf = '('+lat+','+lon+')'
                    tmp_list.append(ymp_gps_inf)
                gps_inf = ','.join(list(tmp_list))
        # if(gps_inf!=''):
        gps_list.append(gps_inf)
        
    cv1 = {
        'oid': oid_list,
        'n': Transaction_len,
        'Transaction': Transaction
    }

    cv2 = {
        'oid': oid_list,
        '產品名稱': procuct_name,
        '產出組織': organization,
        '產出日期': sell_date,
        '有效日期': available_date,
        '產出數量': product_num,
        '剩餘數量': left_num,
    }      

    cv3 = {
        'oid': oid_list,
        'gps': gps_list
    } 

    cv4 = {
        'oid': oid_list,
        'n': Transaction_len,
        'Transaction': Transaction,
        '產品名稱': procuct_name,
        '產出組織': organization,
        '產出日期': sell_date,
        '有效日期': available_date,
        '產出數量': product_num,
        '剩餘數量': left_num,
    } 

    data1 = pd.DataFrame(cv1)
    data2 = pd.DataFrame(cv2)
    data3 = pd.DataFrame(cv3)
    data4 = pd.DataFrame(cv4)
    data3 = data3[ data3['gps']!='' ]
    data1.to_csv('result/result_1.csv',index=False,encoding='utf-8-sig')
    data2.to_csv('result/result_2.csv',index=False,encoding='utf-8-sig')
    data3.to_csv('result/result_3.csv',index=False,encoding='utf-8-sig')
    data4.to_csv('result/result_4.csv',index=False,encoding='utf-8-sig')

def check_datasource(path):
    if(os.path.exists(path)):
        if(os.path.isdir(path)):
            preprocess(path)
        else:
            print('{}/ is not a folder path'.format(path))
    else:
        print('{}/ doesn\'t exists'.format(path)) 

def main():
    mkdir('result')
    check_datasource(args.path)

# Usage: python r08922156_project_C.py data
if __name__ == "__main__":
    main()