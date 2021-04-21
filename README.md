# damaiapp_interview
程式流程主要為三個部分，一開始利用python清理資料，資料非常龐大(1G)，但其實當中有大量重複，且有些特殊字元會導致UFT-8無法正常讀取。透過前處理後將重複的資料以及錯誤的資料修正後打上Google Sheet。利用Google sheet 當作短期的db使用。並且撰寫app script 做為管理db的角色。最後再android端，先將所有的景點load近來，接著利用字串contains的方式做到搜尋功能，接著利用ID與API串接獲得景點更多的資訊，最後將備註上傳至雲端即可做到存檔的功能。
* data_preprocessing.py
  * 清理資料
  * 上傳至Google sheet
* databaseManager.js
  * 接收清理完成的資料
  * 把資料放入指定的欄位
  * 開API給android端
  * 修改/備註
* damaiapp_interview(Android project)
  * 將API拿到的景點list出來
  * 透過id向API查找資料
  * 搜尋-把名字中含有的關鍵字顯示出來
  * 將修改的備註上傳至API

[影片Demo](https://youtu.be/ZGAL1h9R2UM)   
[臺北旅遊網景點資料](https://data.taipei/#/dataset/detail?id=bd31c976-d3a5-4eed-b8c3-7454bc266afa)   
[Google Sheet Link1](https://docs.google.com/spreadsheets/d/1CDDyIj8ghcOUUchAr8seWDMbKNWDiQoTomvCL6JQflI/edit#gid=0)    
[Google Sheet Link2](https://docs.google.com/spreadsheets/d/1RKJHz3AhgctjX6WowiRby19w5ZI6MVLipcLLdJqcKx0/edit#gid=0)   
[APK Download](https://drive.google.com/file/d/1eOBBnB86E18-pkkDeWhSYCKemAywnNp4/view?usp=sharing)
