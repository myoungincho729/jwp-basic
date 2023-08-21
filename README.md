#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* ServletContext가 초기화되면 컨텍스트의 초기화 이벤트가 발생된다. web.xml이나 @WebServlet 어노테이션이 붙은 클래스들을 url과 매핑시킨다.(초기화 x)
* ServletContextListener를 구현하는 ContextLoaderListener에서 contextInitialized 메서드를 호출한다. 여기서 jwp.sql이 실행된다.
* 서블릿 컨테이너는 클라이언트 최초 요청시 DispatcherServlet을 생성한다. 이 프로젝트에서 loadOnStartUp=1 로 설정했기 때문에 최초 요청시가 아닌 서블릿컨테이너 시작시점에 인스턴스를 생성한다.
* DispatcherServlet의 init메서드를 호출한다. 
* RequestMapping 인스턴스를 생성한다. 여기서 우리가 정의한 url-controller 매핑이 일어난다.


#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 모든 요청은 DispatcherServlet으로 간다("/" 옵션)
* 요청이 도달하기 전에 ResourceFilter 서블릿 필터를 거친다. 필터에서는 요청 URL이 resourcePrefixs 안에 포함되는지 여부를 확인한다.
* DispatcherServlet의 service메서드가 호출된다.
* RequestMapping에서 요청URL을 처리할 수 있는 컨트롤러를 찾아 반환한다. "/"를 처리하는 HomeController를 반환한다.
* HomeController에서는 JspView("home.jsp"), Model(List<User>)를 가지는 ModelAndView를 반환한다. 
  * List<User>를 찾기 위해 JdbcTemplate의 findAll 메서드를 호출한다. 이때 db커넥션을 맺고 sql을 실행 후 반환값을 rowMapper에서 정의한대로 List<User>를 가져온다.
* DispatcherServlet은 HomeController가 반환한 ModelAndView를 리턴받고 view.render 메서드를 실행한다.
  * JspView의 render 메서드에서는 Model(Map)이 가진 값들을 request - setAttribute로 세팅한 후 viewName에 있는 jsp파일로 forward한다.
  * Jsp에서는 모델 데이터를 이용해서 HTML생성하고 응답한다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* ShowController는 QuestionDao, AnswerDao, Question, List<Answer>필드를 private으로 공유하고 있음
* A가 ```/qna/show?questionId=1```요청을 보내고 B가 ```/qna/show?questionId=2``` 요청을 보내는 경우를 생각해보자.
* A의 요청은 ShowController에서 1번 질문에 대한 질문 내용과 답변들을 Dao에서 가져온다.(question -> 1번질문내용, answers-> 1번질문의 답변들)
* 만약 A요청이 끝나기 전에, 즉 ShowController의 service메서드가 끝나기 전에 B의 요청을 처리할 경우에 B요청을 처리하는 쓰레드에서 ShowController-service가 실행되고, Dao에서 2번질문에 대한 질문-답변을 가져오게 된다.(question -> 2번질문 내용, answers -> 2번질문 답변들)
* 각 쓰레드는 Dao, question, answers를 공유하기 때문에 A 요청의 응답으로 2번질문에 대한 내용과 답변들을 반환하게 된다.
* 따라서 A는 1번 질문 상세페이지를 원했지만 2번 질문의 상세페이지를 응답받는다.
* 이를 해결하기 위해서는 각 요청마다 Question, List<Answer>를 따로 반환할 수 있도록 해당 필드를 로컬변수로 선언해야 한다.
```java
public class ShowController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();
    private Question question;
    private List<Answer> answers;

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        question = questionDao.findById(questionId);
        answers = answerDao.findAllByQuestionId(questionId);
      ...
```
위의 코드에서 아래의 코드로 수정한다.
```java
public class ShowController extends AbstractController {
    private QuestionDao questionDao = new QuestionDao();
    private AnswerDao answerDao = new AnswerDao();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
        Long questionId = Long.parseLong(req.getParameter("questionId"));

        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);
      ...
```

