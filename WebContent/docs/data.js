/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function(){
   
    
    $('* td').each(function () {   
    var item = $(this).text();
    var num = Number(item).toLocaleString('en');

    if (Number(item) < 0) {
        num = num.replace('-', '');
        $(this).addClass('negMoney');
    } else {
        $(this).addClass('enMoney');
    }

    $(this).text(num);
});
    
    function isEmpty(val){
        return (val === undefined || val == null || val.length <= 0) ? true : false;
    }
    console.log("Before: ");
    $.getJSON("http://localhost:8080/PredictiveAnalyticsApp/data/service/retrievelocal", function(result){
        $.each(result, function(i, field){
            
            //pensString = JSON.parse(JSON.stringify(result));
        	jsonData = JSON.parse(JSON.stringify(result));
        	
        	for (var i = 0; i < jsonData.data.length; i++) {
        	    var counter = jsonData.data[i];
        	    for (var j = 0; j < counter.allData.length; j++) {
            	    var item = counter.allData[j];
            	    console.log("Item: " + item.reccategory);
            	    console.log("Item: " + item.recmessage);
            	    console.log("Item: " + item.recduration);
            	    console.log("Item: " + item.recbookmark);
            	    console.log("Item: " + item.recvisits);
            	    console.log("Item: " + item.reccontactnumber);
            	    console.log("Item: " + item.reclatitude);
            	    console.log("Item: " + item.recordid);
            	    console.log("Item: " + item.recdirection);
            	    console.log("Item: " + item.reclongitude);
            	    console.log("Item: " + item.recphoneid);
            	    console.log("Item: " + item.rectitle);
            	    console.log("Item: " + item.reccontactname);
            	    console.log("Item: " + item.recurl);
            	    console.log("Item: " + item.recsyncedbool);
            	}
        	}
        	
            /*for (var counter in pensString.data.allData) {
                console.log("We are logging: "+ counter.reccategory);
            }*/
            
            
        });
    });
    console.log("After: ");
    
});

