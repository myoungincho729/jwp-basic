// $(".qna-comment").on("click", ".answerWrite input[type=submit]", addAnswer);
$(".answerWrite input[type=submit]").click(addAnswer);

function addAnswer(e) {
  e.preventDefault();

  var queryString = $("form[name=answer]").serialize();

  $.ajax({
    type : 'post',
    url : '/api/qna/addAnswer',
    data : queryString,
    dataType : 'json',
    error: onError,
    success : onSuccess,
  });
}

function onSuccess(json, status){
  var answer = json.answer;
  var countOfComments = json.question.countOfComment;
  var answerTemplate = $("#answerTemplate").html();
  var template = answerTemplate.format(answer.writer, new Date(answer.createdDate), answer.contents, answer.answerId, answer.answerId);
  $(".qna-comment-slipp-articles").prepend(template);
  $(".qna-comment-count strong").text(countOfComments);
  $(".answerWrite input[name=writer]").val("");
  $(".answerWrite textarea[name=contents]").val("");
}

function onError(xhr, status) {
  alert("error");
}

String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".qna-comment").on("click", ".form-delete", deleteAnswer);

function deleteAnswer(e) {
  e.preventDefault();

  var deleteBtn = $(this);
  var queryString = deleteBtn.closest("form").serialize();
  console.log("qs : " + queryString);

  $.ajax({
    type: 'post',
    url: "/api/qna/deleteAnswer",
    data: queryString,
    dataType: 'json',
    error: function (xhr, status) {
      alert("error");
    },
    success: function (json, status) {
      var result = json.result;
      var countOfComments = json.question.countOfComment
      if (result.status) {
        deleteBtn.closest('article').remove();
        $(".qna-comment-count strong").text(countOfComments);
      }
    }
  });
}