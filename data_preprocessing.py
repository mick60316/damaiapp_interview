import io
import json
import codecs
import time
from json.decoder import JSONDecodeError
import requests
#lines = f.read()
#print (lines)
googlesheetUrl = "https://script.google.com/macros/s/AKfycbyvk9Gxa5UisCySFYasojru2sZnsazNxHqP1FrjduFeHN-5RCs/exec"
idList =[]


def researchAllAttractions (_inputdata):
    index =0
    while True:
        rangeStart = 0 
        leftRange = _inputdata.find("{",rangeStart)
        rightRange=_inputdata.find("}",rangeStart)

        if (leftRange ==-1 and rightRange ==-1):
            break

        attractionInfoStr=_inputdata[leftRange:rightRange+1]

        try:
            attractionInfoJson = json.loads(attractionInfoStr)

            if attractionInfoJson ['id'] not  in idList:
                idList.append(attractionInfoJson ['id'])
                attractionInfos = {'action': 'info', 
                'id': attractionInfoJson ['id'],
                "intro":attractionInfoJson['intro'],
                "status":attractionInfoJson['status'],
                "language":attractionInfoJson['language'],
                "name":attractionInfoJson['name'],
                "open_status":attractionInfoJson['open_status'],
                "latitude":attractionInfoJson['latitude'],
                "longitude":attractionInfoJson['longitude'],
                "open_time":attractionInfoJson['open_time'],
                "tel":attractionInfoJson['tel'],
                "fax":attractionInfoJson['fax'],
                "email":attractionInfoJson['email'],
                "management":attractionInfoJson['management'],
                "contact":attractionInfoJson['contact'],
                "months":attractionInfoJson['months'],
                "ticket":attractionInfoJson['ticket'],
                "official_site":attractionInfoJson['official_site'],
                "remind":attractionInfoJson['remind'],
                "county":attractionInfoJson['county'],
                "distric":attractionInfoJson['distric'],
                "address":attractionInfoJson['address'],
                "zipcode":attractionInfoJson['zipcode'],
                "update":attractionInfoJson['update'],
                "film":attractionInfoJson['film'],
                "audio":attractionInfoJson['audio'],
                "categories":attractionInfoJson['categories'],
                "guide_service":attractionInfoJson['guide_service'],
                "services":attractionInfoJson['services'],
                "target":attractionInfoJson['target'],
                "images":attractionInfoJson['images']
                }
                r=requests.post (googlesheetUrl,attractionInfos)
                print (r.text)
                print (attractionInfoJson ['id'],attractionInfoJson['name'])
        except JSONDecodeError as e:
            print (attractionInfoStr,"\n")
        #dataJson=json.loads(_inputdata[leftRange:rightRange+1])
        index+=1
        #print (dataJson ['id'],dataJson['name'])
        
        _inputdata=_inputdata[rightRange+2:]
    print (index)
        

    pass
def main ():
    f = open("data.txt","r",encoding='utf-8')
    rowData = f.read()
    dataStart = rowData.find("data")
    

    dataStr = rowData [dataStart:]
    researchAllAttractions(dataStr)
    
    '''
    print ()
    a=data.find("{",dataStart)
    b=data.find("}",dataStart)+1
    print (a,b)
    print (data[a:b])
    '''
    
    #
    #print (len (data))
 
    #dataJson=json.loads(decoded_data)
    #print (dataJson['data'])

if  __name__=="__main__":

    
    
    main()

    