
$(document).ready(function(){
    $("#searchBtn").click(function(){
        $.ajax({
            type:"GET",
            url:" http://localhost:8080/listAll",
            dataType:"json",
            success:function(data){
                console.log(data);
                for(var i=0;i < data.length;i++){
                    // alert(data.toString);
                    // alert(jsonObj.id);
                    console.log(data[i].username);
                    $("#myH1").text(data[i].username);
                }

            },
            error:function(jqXHR){
                console.log("Error: "+jqXHR.status);
            }
        });
    });
});