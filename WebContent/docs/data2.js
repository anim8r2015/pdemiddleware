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
          { data: "reccontactname"}, 
          { data: "reccontactnumber" }, 
          { data: "reccategory"}
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