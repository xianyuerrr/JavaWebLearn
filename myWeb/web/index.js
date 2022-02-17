function del(classCode){
    if(confirm('是否确认删除？')){
        window.location.href='delete?classCode='+classCode;
    }
}

function goPage(pageNo){
    window.location.href="index?page="+pageNo;
}
