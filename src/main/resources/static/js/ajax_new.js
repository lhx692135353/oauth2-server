/**
 * Created by admin on 2016/4/24.
 */
function useajax(url,data){
    var msg =null;
    $.ajax({
        type:"POST",
        url:url,
        data:data,
        datatype: "json",
        async:false,
        success:function(data){
            msg=data;

        },
        error: function(){
            alert("请求异常");
        }
    });
    return msg;
}

