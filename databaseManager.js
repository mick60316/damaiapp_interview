var infoBook = SpreadsheetApp.openByUrl('https://docs.google.com/spreadsheets/d/1CDDyIj8ghcOUUchAr8seWDMbKNWDiQoTomvCL6JQflI/edit#gid=0'); 
var introBook =SpreadsheetApp.openByUrl('https://docs.google.com/spreadsheets/d/1RKJHz3AhgctjX6WowiRby19w5ZI6MVLipcLLdJqcKx0/edit#gid=0'); 
function doGet (e)
{
  var para=e.parameter;
  
  var act =para.action;
  var infoSheet =infoBook.getSheetByName("Info");
  var introSheet =introBook.getSheetByName("Intro");
  if (act =="all"){
    var dataJsonArray =[];
    
    var lastColumn = infoSheet.getLastColumn();
    var lastRaws =infoSheet.getLastRow();
    
    var data = infoSheet.getRange(1,1, lastRaws,lastColumn).getValues();
    
    for (var i =0;i<data.length;i++)
    {
      if (data[i][0] !=""){
        var jsonObj ={
          "id":data[i][0],
          "name":data[i][3]
          
        }
        dataJsonArray.push(jsonObj);
      }
    }
    var json={};
    json['data']=dataJsonArray;
    return ContentService.createTextOutput(JSON.stringify(json) ).setMimeType(ContentService.MimeType.JSON); 
  }
  else if  (act =="search")
  {
    var id =para.id;
    var lastColumn = infoSheet.getLastColumn();
    var data = infoSheet.getRange(parseInt(id),1, 1,lastColumn).getValues();
    var intro=introSheet.getRange(parseInt(id),1, 1,2).getValues();
    
    var jsonObj ={
      "id":data[0][0],
      "status":data[0][1],
      "language":data[0][2],
      "name":data[0][3],
      "open_status":data[0][4],
      "latitude":data[0][5],
      "longitude":data[0][6],
      "open_time":data[0][7],
      "tel":data[0][8],
      "fax":data[0][9],
      "email":data[0][10],
      "management":data[0][11],
      "contact":data[0][12],
      "months":data[0][13],
      "ticket":data[0][14],
      "official_site":data[0][15],
      "remind":data[0][16],
      "county":data[0][17],
      "distric":data[0][18],
      "address":data[0][19],
      "zipcode":data[0][20],
      "update":data[0][21],
      "film":data[0][22],
      "audio":data[0][23],
      "categories":data[0][24],
      "guide_service":data[0][25],
      "services":data[0][26],
      "target":data[0][27],
      "images":data[0][28],
      "intro":intro[0][1]
      
    }
    return ContentService.createTextOutput(JSON.stringify(jsonObj) ).setMimeType(ContentService.MimeType.JSON); 
  }
  
  
 
}
function doPost (e){

  write_data(e.parameter);
}

function myFunction() {
  
  
}
function write_data (para)
{
   var act=para.action;
  
/*      playid = para.playid,
      fullname =para.fullname,
      playtype= para.playtype,
      team =para.team,
      momnetCount =para.momnetcount;*/
  if (act== "info")
  {
    var id =para.id,
        status =para.status,
        language =para.language,
        name =para.name,
        open_status=para.open_status,
        latitude=para.latitude,
        longitude=para.longitude,
        open_time=para.open_time,
        tel=para.tel,
        fax=para.fax,
        email=para.email,
        management=para.management,
        contact=para.contact,
        months =para.months,
        ticket=para.ticket,
        official_site=para.official_site,
        remind ="",
        county=para.county,
        distric=para.distric,
        address=para.address,
        zipcode=para.zipcode,
        update=para.update,
        film =para.film,
        audio =para.audio,
        categories=para.categories,
        guide_service=para.guide_service,
        services=para.services,
        target=para.target,
        images=para.images;
    var intro =para.intro;
    
    var introSheet =introBook.getSheetByName("Intro");//book中的spreadsheet name
    var dataRange=introSheet.getRange(parseInt(id)+1,1 , 1, 2);
    dataRange.setValues([[id,intro]]);
    var infoSheet =infoBook.getSheetByName("Info");
    dataRange=infoSheet.getRange(parseInt(id)+1,1 , 1, 29);
    dataRange.setValues([[id, status, language, name, open_status,latitude,longitude,open_time,tel,fax,email,management,contact,months,ticket,official_site,remind,county,distric,address,zipcode,update,film,audio,categories,guide_service,services,target,images]]);
        
   
  }
  else if (act =="remind")
  {
    var id = para.id;
    var remind = para.remind;
    var infoSheet =infoBook.getSheetByName("Info");
    dataRange=infoSheet.getRange(parseInt(id),17 , 1, 1);
    dataRange.setValues([[remind]]);
  }
  

}
