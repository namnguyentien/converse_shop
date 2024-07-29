function showImage(btn){ 
    let id = $(btn).data('id');
    $('.product-image[data-id = '+id+']').toggle('show');
}

$('.create-product-button button').on("click",function(){

    $('#create-product-title').show();
    $('#update-product-title').hide();
    $('.btn-create-product').show();
    $('.btn-update-product').hide();
    $('.input-product-image').remove();
    $('#input-product-name').val('');
    $('#product-category').val('');
    $('#input-product-price').val('');
    $('#input-product-quantity').val('');
    $('#input-product-description').val('');
    $('#input-product-modal').modal('show');
})

function showImage(btn){
    let id = $(btn).data('id');
    $('.product-list-image[data-id = '+id+'] .product-image').toggle('show');
}

// Open - Close Choose Image

function chooseImage(btn){
    if($(btn).hasClass("choose")){
        $(btn).toggleClass("choose");
        $('.btn-choose-img').prop("disabled",true);
        $('.btn-delete-img').prop("disabled",true);
    }else{
        $('.list-image .choose').toggleClass('choose');
        $(btn).toggleClass('choose');
        $('.btn-choose-img').prop("disabled",false);
        $('.btn-delete-img').prop("disabled",false);
    }
}

function closeChooseImgModal(){
    $('#choose-image-modal').modal('hide');
    $('.list-image .image-block').removeClass("choose");
    $('.btn-choose-img').prop("disabled",true);
    $('.btn-delete-img').prop("disabled",true);
}

function removeProductImage(btn){
    $(btn).parent().remove();
}

$('#btn-upload-product-img').on("click",function(){
    $('#choose-image-modal').modal('show');
})


var listProduct = [];
        
        let pageNo =0;
        
        function getPage(btn){
            let number = $(btn).text();
            pageNo = number - 1;
            getListProduct(pageNo);
        }


        function getListProduct(pageNo){
            $.ajax({
            url: '/api/product/pageProduct',
            data:{
                pageNo: pageNo,
                pageSize : 8
            },
            type: 'GET',
            success: function(data){
                listProduct = data.content;
                showProduct(data.content);
            }
            })
        }
        $(document).ready(function(){
            getListProduct(pageNo);
        })
        function showProduct(data){
            $('.list-product').children().remove();
            let html = ``;
            $.each(data,function(i,res){
                $.each(res.images,function(i,y){
                html += `<div><img src="data:image/jpeg;base64,${y.data}" data-id = "${y.id}" /></div>`
            })
            $('.list-product').append(`
                    <tr>
                        <td class="product-name" data-id= "${res.id}">${res.name}</td>
                        <td class="product-category-name" data-category = "${res.category.id}" data-id= "${res.id}">${res.category.name}</td>
                        <td class="product-price" data-id= "${res.id}">${res.price}</td>
                        <td class="product-quantity" data-id= "${res.id}">${res.quantity}</td>
                        <td class="product-description" data-id= "${res.id}">${res.description}</td>
                        <td class = "product-list-image" data-id = "${res.id}">
                            <a type="button" class="show-image-button" data-id = "${res.id}" onclick="showImage(this)"><i class="fa-solid fa-image"></i></a>
                            <div class = "product-image" >
                                ${html}
                            </div>
                        </td>
                        <td class="product-enable" data-id= "${res.id}"><button class="enable-button btn btn-info">Enabled</button></td>
                        <td class="product-action" >
                            <button class="btn btn-primary update-product-button" onclick="updateProduct(this)" data-id= "${res.id}">Update</button>
                            <button class="btn btn-danger delete-product-button" onclick ="deleteProduct(this)" data-id= "${res.id}">Delete</button>
                        </td>
                    </tr>
            `)
            html = ``;
            $('.product-image').hide();
            })
        }


        function updateProduct(btn){

            $('.input-product-image').remove();
            $('#create-product-title').hide();
            $('#update-product-title').show();
            $('.btn-create-product').hide();
            $('.btn-update-product').show();

            let id = $(btn).data('id');
            let name = $('.product-name[data-id = '+id+']').text();
            let price = $('.product-price[data-id = '+id+']').text();
            let description = $('.product-description[data-id = '+id+']').text();
            let quantity = $('.product-quantity[data-id = '+id+']').text();
            let category = $('.product-category-name[data-id = '+id+']').data('category');
            let html = ``;
            $('.product-list-image[data-id = '+id+'] img').each(function(){
                let url = $(this).attr('src');
                let image_id = $(this).data('id');
                html += `
                    <div class = "input-product-image">
                        <img src="${url}" alt="product img" data-id ="${image_id}">
                        <div class="delete-input-image" onclick="removeProductImage(this)"><i class="fas fa-times-circle"></i></div>    
                    </div>
                `
            })
            $('#input-product-id').val(id);
            $('#input-product-name').val(name);
            $('#product-category').val(category);
            $('#input-product-price').val(price);
            $('#input-product-quantity').val(quantity);
            $('#input-product-description').val(description);
            $('.list-product-image').append(`
                ${html}
            `);
            $('#input-product-modal').modal('show');
        }

        // Upload Image
        $('#upload-thumbnail').on("change",function(){
            var fd = new FormData();
            var file = $(this)[0].files[0];
            fd.append('file',file);
            $.ajax({
                url: "/admin/api/upload-file",
                type: "POST",
                data: fd,
                contentType: false,
                processData: false,
                success: function(data){
                    let url = data.data;
                    $('.list-image').append(`
                        <div class = "image-block" onclick="chooseImage(this)">
                            <img src="data:image/jpeg;base64,${url}" alt="" data-id = "${data.id}">
                        </div>
                    `)
                }
            })
        })


        $('.btn-choose-img').on("click",function(){
            let url = $('.list-image .choose img').attr('src');
            let data = $('.list-image .choose img').data('id');
            closeChooseImgModal();
            $('.list-product-image').append(`
                <div class = "input-product-image">
                    <img src="${url}" alt="product img" data-id ="${data}">
                    <div class="delete-input-image" onclick="removeProductImage(this)"><i class="fas fa-times-circle"></i></div>
                </div>
            `);
        })



        $('.btn-create-product').on("click",function(){

            $('.invalid-feedback').hide();
            let name = $('#input-product-name').val();
            let category_id = $('#product-category').val();
            let price = $('#input-product-price').val();
            let quantity = $('#input-product-quantity').val();
            let description = $('#input-product-description').val();
            let html = ``;
            let image_ids = [];
            $('.list-product-image .input-product-image img').each(function(){
                let url = $(this).attr('src');
                let id = $(this).data('id');
                image_ids.unshift(id);
                html += `<div><img src="${url}" alt="product img" data-id ="${id}"></div>`;
            })
            
            let is_valid = true;
            if(name.length == 0 || name.length > 300){
                $('#invalid-feedback-name').show();
                is_valid =false;
            }
            if(category_id.length == 0){
                $('#invalid-feedback-category').show();
                is_valid =false;
            }
            if(isNaN(price)|| price < 1000 || price > 100000000000){
                $('#invalid-feedback-price').show();
                is_valid = false;
            }

            if(quantity == 0){
                $('#invalid-feedback-quantity').show();
                is_valid =false;
            }

            if(description.length == 0){
                $('#invalid-feedback-description').show();
                is_valid = false;
            }


            if(html == ``){
                $('#invalid-feedback-image').show();
                is_valid = false;
            }

            let req = {
                name : name,
                category_id : category_id,
                description : description,
                price : price,
                quantity: quantity,
                image_ids : image_ids
            }
            let myJSON = JSON.stringify(req);
            if(is_valid){
                $.ajax({
                    url: '/api/product/create',
                    type: 'POST',
                    data: myJSON,
                    contentType: "application/json; charset=utf-8",
                    success: function(data){
                        getListProduct();
                        showProduct(listProduct);
                        $('.modal').modal('hide');
                    },error: function(data){
                    }
                })
            }


        })
        
        $('.btn-update-product').on("click",function(){
            $('.invalid-feedback').hide();
            let id = $('#input-product-id').val();
            let name = $('#input-product-name').val();
            let category_id = $('#product-category').val();
            let price = $('#input-product-price').val();
            let quantity = $('#input-product-quantity').val();
            let description = $('#input-product-description').val();
            let html = ``;
            let image_ids = [];
            $('.list-product-image .input-product-image img').each(function(){
                let url = $(this).attr('src');
                let id = $(this).data('id');
                image_ids.unshift(id);
                html += `<div><img src="${url}" alt="product img" data-id ="${id}"></div>`;
                console.log(html);
            })

            let is_valid = true;
            if(name.length == 0 || name.length > 300){
                $('#invalid-feedback-name').show();
                is_valid =false;
            }
            if(category_id.length == 0){
                $('#invalid-feedback-category').show();
                is_valid =false;
            }
            if(isNaN(price)|| price < 1000 || price > 100000000000){
                $('#invalid-feedback-price').show();
                is_valid = false;
            }

            if(quantity == 0){
                $('#invalid-feedback-quantity').show();
                is_valid =false;
            }

            if(description.length == 0){
                $('#invalid-feedback-description').show();
                is_valid = false;
            }


            if(html == ``){
                $('#invalid-feedback-image').show();
                is_valid = false;
            }

            let req = {
                name : name,
                category_id : category_id,
                description : description,
                price : price,
                quantity: quantity,
                image_ids : image_ids
            }
            let myJSON = JSON.stringify(req);
            $.ajax({
                url: '/api/product/update/' + id,
                type: 'PUT',
                data: myJSON,
                contentType: "application/json; charset=utf-8",
                success: function(data){
                    $('.product-name[data-id='+id+']').text(data.name);
                    $('.product-category-name[data-id='+id+']').text(data.category.name);
                    $('.product-price[data-id='+id+']').text(data.price);
                    $('.product-quantity[data-id='+id+']').text(data.quantity);
                    $('.product-description[data-id='+id+']').text(data.description);
                    $('.product-list-image[data-id ='+id+'] .product-image').remove();
                    $('.product-list-image[data-id = '+id+']').append(`
                        <div class = "product-image">${html}</div>
                    `);
                    $('.product-image').hide();
                    toastr.success("Cập nhật thành công")
                    $('.modal').modal('hide');
                },error: function(data){
                    toastr.error("Cập nhật thất bại")
                }
            })
        })

        function deleteProduct(btn){
            let id = $(btn).data('id');
            
            $.ajax({
                url :'/api/product/delete/' +id,
                type: 'DELETE',
                contentType: "application/json; charset=utf-8",
                success: function(data){
                    getListProduct();
                    showProduct(listProduct);
                    $('.modal').modal('hide');
                    toastr.warning("Xóa thành công");

                },error:function(data){
                    toastr.error("Lỗi khi xóa");

                }
            })
        }