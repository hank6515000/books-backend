window.onload = function () {
    let vm = new Vue({
        el:"#dashboard",
        data:{
            //葉面跳轉控制
            manage:"",
            search:"",
            //關鍵字
            keywordStr:"",
            keywordClass:"",
            keywordUser:"",
            keywordbName:"",
            //數據
            bookClass:"",
            userPage:"",
            bookPage:"",
            orderPage:"",
            contactPage:"",
            replyPage:"",
            //詳情
            orderDetail:"",
            content:""
        }
        ,methods: {
            //依訂單編號搜尋
            searchOrderNo: function (pn) {
                vm.search = '';
                axios({
                    method: "GET",
                    url: "/searchOrderNo",
                    params: {
                        keyword: vm.keywordStr,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'order';
                    vm.search = 'orderNo';
                    vm.orderPage = value.data.data.orderPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //依日期搜尋
            searchOrderDate: function (pn) {
                vm.search = '';
                console.log($("#date_search").val())
                axios({
                    method: "GET",
                    url: "/searchOrderDate",
                    params: {
                        keyword: $("#date_search").val(),
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'order';
                    vm.search = 'date';
                    vm.orderPage = value.data.data.orderPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //依用戶名搜尋
            searchOrder: function (pn) {
                vm.search = '';
                axios({
                    method: "GET",
                    url: "/searchOrder",
                    params: {
                        keyword: vm.keywordUser,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'order';
                    vm.search = 'user';
                    vm.orderPage = value.data.data.orderPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //顯示訂單
            getOrder: function (pn) {
                vm.manage = 'order'
                vm.search = '';
                vm.keywordStr = '';
                axios({
                    method: "GET",
                    url: "/getOrder",
                    params: {
                        pn: pn
                    }
                }).then(function (value) {
                    vm.orderPage = value.data.data.orderPage;
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //刪除訂單
            delOrder: function (oid, pn) {
                Swal.fire({
                    title: '確認要刪除此筆訂單嗎',
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '刪除',
                    cancelButtonText: '取消',
                }).then((result) => {
                    if (result.isConfirmed) {
                        axios({
                            method: "DELETE",
                            url: "/delOrder/" + oid
                        }).then(function (value) {
                            if (value.data.code == 200) {
                                Swal.fire(
                                    '刪除成功',
                                    '您已刪除此筆訂單',
                                    'success'
                                )
                            } else {
                                Swal.fire(
                                    '刪除失敗',
                                    value.data.msg,
                                    'error'
                                )
                            }
                            vm.toPage(vm.manage, vm.search, pn);
                        }).catch(function (reason) {
                            console.log(reason)
                        })
                    }
                })
            },
            //顯示訂單詳情
            showOrderDetail: function (id) {
                console.log(id)
                if (vm.orderDetail == 'detail' + id) {
                    vm.orderDetail = '';
                } else {
                    vm.orderDetail = 'detail' + id;
                }
            },
            //搜尋書籍類別
            classSearch: function (pn) {
                vm.manage = 'product';
                vm.search = '';
                console.log(vm.keywordClass)
                axios({
                    method: "GET",
                    url: "/classSearch",
                    params: {
                        keyword: vm.keywordClass,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'product';
                    vm.search = 'class';
                    vm.bookPage = value.data.data.bookPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //搜尋商品
            searchProduct: function (pn) {
                vm.manage = 'product';
                vm.search = '';
                axios({
                    method: "GET",
                    url: "/searchProduct",
                    params: {
                        keyword: vm.keywordStr,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'product';
                    vm.search = 'search';
                    vm.bookPage = value.data.data.bookPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //顯示商品
            getProduct: function (pn) {
                vm.manage = 'product'
                vm.search = '';
                vm.keywordStr = '';
                axios({
                    method: "GET",
                    url: "/getProduct",
                    params: {
                        pn: pn
                    }
                }).then(function (value) {
                    vm.bookPage = value.data.data.bookPage
                    vm.bookClass = value.data.data.bookClass
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //顯示書籍內容
            showContent: function (bid) {
                if (vm.content == 'content' + bid) {
                    vm.content = '';
                } else {
                    vm.content = 'content' + bid;
                }
            },
            //添加書籍類別
            addBookClass: function (pn) {
                axios({
                    method: "POST",
                    url: "/addBookClass",
                    params: {
                        bookClass: $("#addBookClass_input").val()
                    }
                }).then(function (value) {
                    if (value.data.code == 200) {

                        Swal.fire(
                            '添加成功',
                            '您已添加書籍類別',
                            'success'
                        )
                        $("#addBookClass_Modal").modal("hide");
                        vm.toPage(vm.manage, vm.search, pn)
                    } else {
                        Swal.fire(
                            '添加失敗',
                            value.data.data.msg,
                            'error'
                        )
                    }
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //添加商品
            addProduct: function (pn) {
                vm.manage = 'product'
                let formData = new FormData();
                if ( $("#bookImg").get(0).files[0]){
                    formData.append('bookImg', $("#bookImg").get(0).files[0])
                    if ($("#name_add_input").val() && $("#author_add_input").val() && $("#price_add_input").val() && $("#inStock_add_input").val()
                    && $("#publishingHouse_add_input").val() && $("#publicationDate_add_input").val() && $("#content_add_input").val()){
                        axios({
                            method: "POST",
                            url: "/addProduct",
                            dataType: "json",
                            data: formData,
                            contentType: false,
                            processData: false,
                            params: {
                                bookName: $("#name_add_input").val(),
                                author: $("#author_add_input").val(),
                                price: $("#price_add_input").val(),
                                bookCount: $("#inStock_add_input").val(),
                                publishingHouse: $("#publishingHouse_add_input").val(),
                                publicationDate: $("#publicationDate_add_input").val(),
                                content: $("#content_add_input").val(),
                                bookClass: $("#bookClass_add_select").val()
                            }
                        }).then(function (value) {
                            if (value.data.code == 200) {
                                Swal.fire('添加成功', '您已添加此商品', 'success')

                                clearForm();
                                $("#add_Modal").modal("hide");
                                vm.getProduct(pn);
                            } else {
                                Swal.fire('添加失敗', value.data.data.msg, 'error')
                            }
                        }).catch(function (reason) {
                            console.log(reason)
                        })
                    }else {
                        Swal.fire('添加失敗', '不可有一處為空', 'error')
                    }
                }else {
                    Swal.fire('添加失敗', '圖片不可為空', 'error')
                }
            },
            //修改商品
            updateProduct: function (pn) {
                vm.manage = 'product'
                let formData = new FormData();
                formData.append('updateImg', $("#updateImg").get(0).files[0])
                let bid = $("#bid_input").val();
                //判斷上傳圖片是否為空
                if ($("#updateImg").get(0).files[0]){
                    console.log("有")
                    //判斷內容是否為空
                    if ($("#name_input").val() && $("#author_input").val() && $("#price_input").val() && $("#publishingHouse_input").val() && $("#publicationDate_input").val()
                        && $("#content_input").val()){
                        axios({
                            method: "PUT",
                            url: "/updateProductHasImg/" + bid,
                            dataType: "json",
                            data: formData,
                            contentType: false,
                            processData: false,
                            params: {
                                bookName: $("#name_input").val(),
                                author: $("#author_input").val(),
                                price: $("#price_input").val(),
                                bookCount: $("#inStock_input").val(),
                                publishingHouse: $("#publishingHouse_input").val(),
                                publicationDate: $("#publicationDate_input").val(),
                                content: $("#content_input").val(),
                                bookClass: $("#bookClass_select").val(),
                            }
                        }).then(function (value) {
                            if (value.data.code == 200) {
                                Swal.fire('修改成功', '您已修改此商品', 'success')

                                $("#update_Modal").modal("hide");
                                vm.getProduct(pn);
                            } else {
                                Swal.fire('修改失敗', value.data.data.msg, 'error')
                            }
                        }).catch(function (reason) {
                            console.log(reason)
                        })
                    }else {
                        Swal.fire('添加失敗', '不可有一處為空', 'error')
                    }

                }else {
                   if ($("#name_input").val() && $("#author_input").val() && $("#price_input").val() && $("#publishingHouse_input").val() && $("#publicationDate_input").val()
                       && $("#content_input").val()){
                       axios({
                           method: "PUT",
                           url: "/updateProduct/" + bid,
                           params: {
                               bookName: $("#name_input").val(),
                               author: $("#author_input").val(),
                               price: $("#price_input").val(),
                               bookCount: $("#inStock_input").val(),
                               publishingHouse: $("#publishingHouse_input").val(),
                               publicationDate: $("#publicationDate_input").val(),
                               content: $("#content_input").val(),
                               bookClass: $("#bookClass_select").val(),
                           }
                       }).then(function (value) {
                           if (value.data.code == 200) {
                               Swal.fire('修改成功', '您已修改此商品', 'success')

                               $("#update_Modal").modal("hide");
                               vm.getProduct(pn);
                           } else {
                               Swal.fire('修改失敗', value.data.data.msg, 'error')
                           }
                       })
                   }else {
                       Swal.fire('添加失敗', '不可有一處為空', 'error')
                   }
                }
            },
            //刪除商品
            delProduct: function (bid, pn) {
                Swal.fire({
                    title: '確認要刪除此書籍嗎',
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '刪除',
                    cancelButtonText: '取消',
                }).then((result) => {
                    if (result.isConfirmed) {
                        axios({
                            method: "DELETE",
                            url: "/delProduct/" + bid
                        }).then(function (value) {
                            if (value.data.code == 200) {
                                Swal.fire(
                                    '刪除成功', '您已刪除此書籍', 'success'
                                )

                            } else {
                                Swal.fire('刪除失敗', value.data.msg, 'error')
                            }
                            vm.toPage(vm.manage, vm.search, pn)
                        }).catch(function (reason) {
                            console.log(reason)
                        })
                    }
                })
            },
            //搜尋會員
            searchMember: function (pn) {
                vm.search = '';
                axios({
                    method: "GET",
                    url: "/searchMember",
                    params: {
                        keyword: vm.keywordStr,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'member';
                    vm.search = 'search';
                    vm.userPage = value.data.data.userPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //顯示會員
            getMember: function (pn) {
                vm.manage = 'member';
                vm.search = '';
                vm.keywordStr = '';
                axios({
                    method: "GET",
                    url: "/getMember",
                    params: {
                        pn: pn
                    }
                }).then(function (value) {
                    vm.userPage = value.data.data.userPage;
                }).catch(function (reason) {

                })
            },
            //刪除會員
            delMember: function (uid, pn) {
                Swal.fire({
                    title: '確認要刪除此會員嗎',
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '刪除',
                    cancelButtonText: '取消',
                }).then((result) => {
                    if (result.isConfirmed) {
                        axios({
                            method: "DELETE",
                            url: "/delMember/" + uid
                        }).then(function (value) {
                            if (value.data.code == 200) {
                                Swal.fire(
                                    '刪除成功',
                                    '您已刪除此會員',
                                    'success'
                                )
                                vm.toPage(vm.manage, vm.search, pn)
                            } else {
                                Swal.fire(
                                    '刪除失敗',
                                    value.data.msg,
                                    'error'
                                )
                            }
                            vm.getMember(pn);
                        }).catch(function (reason) {
                            console.log(reason)
                        })
                    }
                })
            },
            //搜尋留言
            searchContact: function (pn) {
                vm.search = '';
                axios({
                    method: "GET",
                    url: "/searchContact",
                    params: {
                        keyword: vm.keywordStr,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'contact';
                    vm.search = 'search';
                    vm.contactPage = value.data.data.contactPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //顯示留言
            getContact: function (pn) {
                vm.manage = 'contact';
                vm.search = '';
                vm.keywordStr = '';
                axios({
                    method: "GET",
                    url: "/getContact",
                    params: {
                        pn: pn
                    }
                }).then(function (value) {
                    vm.contactPage = value.data.data.contactPage;
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //刪除留言
            delContact: function (cid, pn) {
                Swal.fire({
                    title: '確認要刪除此則留言嗎',
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '刪除',
                    cancelButtonText: '取消',
                }).then((result) => {
                    if (result.isConfirmed) {
                        axios({
                            method: "DELETE",
                            url: "/delContact/" + cid
                        }).then(function (value) {
                            if (value.data.code == 200) {
                                Swal.fire(
                                    '刪除成功',
                                    '您已刪除則留言',
                                    'success'
                                )
                                vm.toPage(vm.manage, vm.search, pn)
                            } else {
                                Swal.fire(
                                    '刪除失敗',
                                    value.data.msg,
                                    'error'
                                )
                            }
                        }).catch(function (reason) {
                            console.log(reason)
                        })
                    }
                })
            },
            //以書籍搜尋回覆
            searchBookName: function (pn) {
                vm.search = '';
                axios({
                    method: "GET",
                    url: "/searchBookName",
                    params: {
                        keyword: vm.keywordbName,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'reply';
                    vm.search = 'bookName';

                    vm.keywordStr = '';
                    vm.keywordUser = '';
                    vm.keywordbName = '';

                    vm.replyPage = value.data.data.replyPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //以用戶搜尋回覆
            searchReplyUser: function (pn) {
                vm.search = '';
                axios({
                    method: "GET",
                    url: "/searchReplyUser",
                    params: {
                        keyword: vm.keywordUser,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'reply';
                    vm.search = 'user';

                    vm.keywordStr = '';
                    vm.keywordUser = '';
                    vm.keywordbName = '';

                    vm.replyPage = value.data.data.replyPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //搜尋回覆
            searchReply: function (pn) {
                vm.search = '';
                axios({
                    method: "GET",
                    url: "/searchReply",
                    params: {
                        keyword: vm.keywordStr,
                        pn: pn
                    }
                }).then(function (value) {
                    vm.manage = 'reply';
                    vm.search = 'reply';

                    vm.keywordStr = '';
                    vm.keywordUser = '';
                    vm.keywordbName = '';

                    vm.replyPage = value.data.data.replyPage
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //刪除回覆
            delReply: function (rid, pn) {
                Swal.fire({
                    title: '確認要刪除此則回覆嗎',
                    icon: 'question',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: '刪除',
                    cancelButtonText: '取消',
                }).then((result) => {
                    if (result.isConfirmed) {
                        axios({
                            method: "DELETE",
                            url: "/delReply/" + rid
                        }).then(function (value) {
                            if (value.data.code == 200) {
                                swal('刪除成功', 'success', '您已刪除則留言')

                                vm.toPage(vm.manage, vm.search, pn)
                            } else {
                                Swal.fire(
                                    '刪除失敗',
                                    value.data.msg,
                                    'error'
                                )
                            }
                        }).catch(function (reason) {
                            console.log(reason)
                        })
                    }
                })
            },
            //顯示回覆
            getReply: function (pn) {
                vm.manage = 'reply'
                vm.search = '';
                vm.keywordStr = '';
                axios({
                    method: "GET",
                    url: "/getReply",
                    params: {
                        pn: pn
                    }
                }).then(function (value) {
                    vm.replyPage = value.data.data.replyPage;
                }).catch(function (reason) {
                    console.log(reason)
                })
            },
            //頁面跳轉邏輯
            toPage: function (manage, search, pn) {
                switch (manage) {
                    case 'member':
                        switch (search) {
                            case 'search':
                                vm.searchMember(pn);
                                break;
                            default:
                                vm.getMember(pn);
                                break;
                        }
                    case 'product':
                        switch (search) {
                            case 'search':
                                vm.searchProduct(pn);
                                break;
                            case 'class':
                                vm.classSearch(pn);
                                break;
                            default:
                                vm.getProduct(pn);
                                break;
                        }
                    case 'order':
                        switch (search) {
                            case 'user':
                                vm.searchOrder(pn);
                                break;
                            case 'orderNo' :
                                vm.searchOrderNo(pn);
                                break;
                            case 'date' :
                                vm.searchOrderDate(pn);
                                break;
                            default:
                                vm.getOrder(pn);
                                break;
                        }
                    case 'contact':
                        switch (search) {
                            case 'search':
                                vm.searchContact(pn);
                                break;
                            default:
                                vm.getContact(pn);
                                break;
                        }
                    case 'reply':
                        switch (search) {
                            case 'reply':
                                vm.searchReply(pn);
                                break;
                            case 'user':
                                vm.searchReplyUser(pn);
                                break;
                            case 'bookName':
                                vm.searchBookName(pn);
                                break;
                            default:
                                vm.getReply(pn);
                                break;
                        }
                }
            }
        }
    })
}

function clearForm(){
        $("#name_add_input").val().empty();
        $("#author_add_input").val().empty();
        $("#price_add_input").val().empty();
        $("#inStock_add_input").val().empty();
        $("#publishingHouse_add_input").val().empty();
        $("#publicationDate_add_input").val().empty();
        $("#content_add_input").val().empty();

}

//點擊增加按鈕後生成bookClass在select中option選項
$(document).on("click","#addModal",function () {
    $("#bookClass_add_select").empty();
    $.ajax({
        url:"/getBook/"+0,
        type:"GET",
        success:function (result){
            let bookClasses = result.data.bookClasses;
            $.each(bookClasses,function (){
                let option = $("<option></option>").append(this.bookClass).attr("value",this.bid).attr("id",this.bid);

                option.appendTo("#bookClass_add_select")
            })
        }
    })
})

//點擊添加按鈕後生成bookClass在select中option選項
$(document).on("click","#updateModal",function (){
    let id = $(this).attr("update_id");
    $("#bookClass_select").empty();
    $.ajax({
        url:"/getBook/"+id,
        type:"GET",
        success:function (result){
            let book = result.data.book;
            let bookClasses = result.data.bookClasses;
            $("#bid_input").val(book.id);
            $("#name_input").val(book.bookname);
            $("#author_input").val(book.author);
            $("#price_input").val(book.price);
            $("#inStock_input").val(book.bookcount);
            $("#publishingHouse_input").val(book.publishinghouse);
            $("#publicationDate_input").val(book.publicatiodate);
            $("#content_input").text(book.content);

            $.each(bookClasses,function (){
                let option = $("<option></option>").append(this.bookClass).attr("value",this.bid).attr("id",this.bid);
                if(option.attr("value") == book.bookclass){
                    option.attr("selected","selected");
                }
                option.appendTo("#bookClass_select")
            })
        }
    })
})






