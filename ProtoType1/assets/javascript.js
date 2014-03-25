function zoomPicture2(ID){
document.getElementById(ID).zoom='300%';
}

function zoomPicture(picture,href){
document.getElementById('content').style.display="none";
document.getElementById('showpicture').style.display="block";
document.getElementById('pictureframe').src=picture;
}

function zoomBackPicture(){
document.getElementById('content').style.display="block";
document.getElementById('showpicture').style.display="none";
}