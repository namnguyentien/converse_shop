function chooseImg(img) {
  if ($(img).hasClass("choosen")) {
    $(img).toggleClass("choosen");
    $(".btn-delete-img").prop("disabled", true);
    $(".btn-choose-img").prop("disabled", true);
  } else {
    $(".grid-item .choosen").toggleClass("choosen");
    $(img).toggleClass("choosen");
    $(".btn-delete-img").prop("disabled", false);
    $(".btn-choose-img").prop("disabled", false);
  }
}
function closeChooseImgModal() {
  $("#choose-img-modal").modal("hide");
  $(".grid-item").removeClass("choosen");
  $(".btn-delete-img").prop("disabled", true);
  $(".btn-choose-img").prop("disabled", true);
}



$(".upload-thumbnail").on("change",function () {
  var fd = new FormData();
  var file = $(this)[0].files[0];

  fd.append("file", file);
  $.ajax({
    url: "/admin/api/upload-file",
    type: "POST",
    data: fd,
    contentType: false,
    processData: false,
    success: function (data) {
      url = data.data;
      $("#list-img ").append(`
              <div onclick="chooseImg(this)" class="grid-item">
                <img
                src = "data:image/jpeg;base64,${url}"
                alt=""
                width="64px"
                height="64px"
                class="grid-item-img"
                data-id = "${data.id}"
              />
              </div>
          `);
    },
    error: function (data) {},
  });
});

$("#btn-upload-product-img").on("click",function () {
  $("#choose-img-modal").modal("show");
  $(".btn-choose-img").on("click", function (event) {
    let url = $(".choosen .grid-item-img").attr("src");
    let data = $(".choosen .grid-item-img").data("id");
    closeChooseImgModal();
    $(".btn-choose-img").off("click");
    $("#list-product-image").append(`
                <div class="grid-item">
                    <div class="img-container">
                        <div class="img-div">
                            <img src="${url}" alt="product img" width="64px" height = "64px" data-id ="${data}">
                        </div>
                    </div>
                    <div class="remove-img" onclick="removeProductImage(this)">
                        <i class="fas fa-times-circle"></i>
                    </div>
                </div>`);
  });
});



$('#btn-update-product-img').on("click",function(){
  $("#choose-update-img-modal").modal("show");
  $("#choose-update-img-modal .btn-choose-img").on("click",function(){
    let url = $(".choosen .grid-item-img").attr("src");
    let data = $(".choosen .grid-item-img").data("id");
    $("#choose-update-img-modal").modal("hide");
    $(".grid-item .choosen").removeClass("choosen");
    $(".btn-delete-img").prop("disabled", true);
    $(".btn-choose-img").prop("disabled", true);
    $(".btn-choose-img").off("click");
    $("#list-update-product-image ").append(`
        <div class="grid-item">
        <div class="img-container">
            <div class="img-div">
            <img src="${url}" alt="product img" width="64px" height = "64px" data-id ="${data}">
            </div>
        </div>
        <div class="remove-img" onclick="removeProductImage(this)">
            <i class="fas fa-times-circle"></i>
        </div>
        </div>
    `);
  })
})