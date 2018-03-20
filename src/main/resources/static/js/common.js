$(function(){
    validate();
})

function validate(){
    $.ajax({
        "url":"/user",
        "type":"GET",
        "dataType":"JSON",
        "success":function (data) {
            if(data==false){
                window.location.href="index"
            }
            if(data){
               var user=data.user;
               var flag=data.flag;
               if(flag==4){
                    $("#findpassword").css("display","");
               }
            }
        }
    })
}