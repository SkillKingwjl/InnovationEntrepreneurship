/**
 * Created by zztaiwll on 18/3/20.
 */
function checkEmail(){
    var email=$("#inputEmail").val();
    var myreg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
    if(!myreg.test(email)){
        alert('请输入有效的E_mail！');
        $("#inputEmail").val("");
        return;
    }

}
function checkPhone(){
    var phone=$("#phone").val();
    var mobile=/^[1][3,4,5,7,8][0-9]{9}$/;
    if(!mobile.test(phone)){
        alert('请输入有效的手机号！');
        return;
    }
    if(phone.length!=11){
        alert('手机号长度不必须是11位！');
        return;
    }
}
function skintoStudent(){
    window.location.href="/register"
}
function skintoteacher(){
    window.location.href="/skintoteacher"
}
function skintocompany(){
    window.location.href="/skintocompany"
}
function doChange(){
    $.ajaxFileUpload({
        url: "/upload", //用于文件上传的服务器端请求地址
        secureuri: false, //是否需要安全协议，一般设置为false
        fileElementId: 'file1', //文件上传域的ID
        dataType: 'json', //返回值类型 一般设置为json
        type:"POST",
        success: function (data, status){
            if(data!='false'){
                var fileName="/upload-dir/"+data.filename
                $("#pic_url").attr("src",fileName);
            }
        },
        error: function (data, status, e)//服务器响应失败处理函数
        {
            alert(e);
        }
    })
}