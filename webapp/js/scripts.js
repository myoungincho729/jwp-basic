String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

//답변하기
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type: 'post',
    url: '/api/qna/addAnswer',
    data: queryString,
    dataType: 'json',
    error: onError,
    success: onSuccess
  })
}

function onError(e) {
  console.log(e);
}

function onSuccess(json, status) {
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(
      json.writer,
      new Date(json.createdDate),
      json.contents,
      json.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
}

//답변삭제하기
$(".form-delete button[type=submit]").click(deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  var btn = $(this);
  var queryString = btn.closest("form").serialize();
  $.ajax({
    type: 'post',
    url: '/api/qna/deleteAnswer',
    data: queryString,
    dataType: 'json',
    error: function (err) {
      console.log(err);
    },
    success: function (json, status) {
      btn.closest(".article").remove();
    }
  })
}
