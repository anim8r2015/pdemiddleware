/**
 * 
 */

$(document).ready(function (){
   $('#phonedata').DataTable({
      lengthChange: false,
      ajax: {
          url: "http://jws-app-pdemidleware.7e14.starter-us-west-2.openshiftapps.com/data/service/retrieve"
      },
      columns: [
			{ data: "recordid",  "defaultContent": ""},
			{ data: "recdatetime",  "defaultContent": ""},
			{ data: "reccategory",  "defaultContent": ""},
			{ data: "recdirection",  "defaultContent": ""}, 
			{ data: "reccontactname",  "defaultContent": ""},
			{ data: "reccontactnumber",  "defaultContent": ""},
			{ data: "recduration", "defaultContent": ""},
			{ data: "recurl", "defaultContent": ""},
			{ data: "recvisits", "defaultContent": ""},
			{ data: "recvisitdate", "defaultContent": ""},
			{ data: "recbookmark",  "defaultContent": ""},
			{ data: "rectitle",  "defaultContent": ""},
			{ data: "recmessage",  "defaultContent": ""},
			{ data: "recbrowsercreated",  "defaultContent": ""},
			{ data: "recphoneid",  "defaultContent": ""}
      ],
      select: true,
      dom: 'Bfrtip',
      buttons: [
          'copyHtml5',
          'excelHtml5',
          'csvHtml5',
          'pdfHtml5'
      ]
   });
});
