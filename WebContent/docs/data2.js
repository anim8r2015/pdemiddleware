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
			{ data: "recordid"},
			{ data: "recdatetime"},
			{ data: "reccategory"},
			{ data: "recdirection"}, 
			{ data: "reccontactname"},
			{ data: "reccontactnumber"},
			{ data: "recsyncdatetime"},
			{ data: "reclatitude"},
			{ data: "reclongitude"},
			{ data: "recsyncedbool"},
			{ data: "recduration"},
			{ data: "recurl"},
			{ data: "recvisits"},
			{ data: "recvisitdate"},
			{ data: "recbookmark"},
			{ data: "rectitle"},
			{ data: "recmessage"},
			{ data: "recbrowsercreated"},
			{ data: "recphoneid"}
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