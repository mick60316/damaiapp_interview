# damaiapp_interview
程式流程分為   
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
