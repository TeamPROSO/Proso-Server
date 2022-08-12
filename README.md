# Proso-Server
🔗[notion/Proso-Server](https://carnelian-cloudberry-496.notion.site/P-Server-d4532ba9ce2a489c9858fc65f851c2d8)

---   
### 🏠 Contributors

안녕하세요! "P로소 보이는 것들"의 서버파트 입니다.

- [김효빈(비니)](https://github.com/hyobim)
- [여민영(이브)](https://github.com/reum2y)

---

### ⚙ Roles

|**기능명**|엔드포인트|담당|구현 진척도|
|:---:|:---:|:---:|:---:|
|소셜 로그인||여민영, 김효빈|
|토큰 재발급||여민영, 김효빈|
|테마 작성||여민영, 김효빈|
||||

---
### 🛠 Used Stacks


**BackEnd**  `Java17` `Spring Boot 2.7.1`  
<br>
**Database** `MySQL 8.0.28`  
<br>
**Server**  `AWS` `EC2`  
<br>
**Repository**   `Github`  

---
### 💻 Code Convention
 1. 인스턴스 변수 또는 클래스 변수를 합당한 이유없이 public으로 선언하지 않습니다.   
    인스턴스 변수들은 명시적으로 선언될 필요가 없는 경우가 많습니다.
 3. **함수에서 else if의 사용을 최대한 줄입니다.**
 4. **연산자 우선순위 문제를 피하기 위해서 복합 연산자를 포함하는 경우에는 괄호를 꼼꼼히 쳐줍니다.(서로의 가독성을 위해서도)**
 5. null 의 사용은 지양합니다. 
 6. 숫자 상수는 카운트 값으로 for 루프에 나타나는 -1, 0, 1을 제외하고는 숫자 자체를 코드에 사용하지 않습니다.
 7. 명명 규칙
     <details>  <summary>1. 변수</summary>  
     <div markdown="1"> 
     <br>
		 1-1. camelCase 형식을 사용합니다.<br><br>
		 1-2. 이름은 짧지만 의미 있어야 합니다.(사용 의도를 누구나 알아낼 수 있도록!)<br><br>
		 1-3. ENUM이나 상수는 대문자로 표기합니다.<br><br>
     </div>  </details>
     
     <details>  <summary>2. 함수</summary>  
     <div markdown="1"> 
     <br>
     2-1. 함수의 이름은 동사여야 하며, camelCase 형식을 사용합니다. <br><br>
		 2-2. 객체 이름을 함수 이름에 중복적으로 사용하지 않습니다.<br><br>
		 </div>  </details>
     
     <details>  <summary>3. 클래스 </summary>  
     <div markdown="1"> 
     <br>
     클래스 이름은 명사이어야 하며 Pascal Case를 사용합니다.
		 </div>  </details>

     <details>  <summary>4. 인터페이스 </summary>  
     <div markdown="1"> 
     <br>
     클래스와 같은 규칙을 사용합니다.
		 </div>  </details>
---

### 💻 Git Convention
<details>  <summary>1. Issue 생성시 규칙</summary>  
<div markdown="1"> 
<br>
이슈 이름 규칙은 [태그]+설명 으로 짓습니다.<br>
ex) [Feat] 어떤거 구현
라벨에 들어가야할 것들은 담당자, 할일, 우선순위! 
 </div>  </details>
 
<details>  <summary>2. Branch 네이밍 규칙</summary>  
<div markdown="1"> 
<br>
issue를 기반한 작업단위, 기능단위로 생성합니다!

issue 만들면 생성되는 번호 + issue 간략 설명을 이용해 브랜치를 만듭니다.

<브랜치명>/<번호>-<설명> 의 양식!

브랜치명 종류로는 main,develop,feature가 있고, 주로 feature 브랜치에서 개발하게됩니다. 

ex)feature/#12-signup
 </div>  </details>


<details>  <summary>3. Commit message 규칙</summary>  
<div markdown="1"> 
<br>
영어 작성을 원칙으로하고 나타내기 어려우면 한글로 합니다.

태그: #이슈넘버 - 간단한 설명 메세지 작성

ex) Feat: #12 - 테스트용입니다.

그리고 안에 간단하게 설명 써줍시다.
 </div>  </details>

---
### 📝 API 명세서

[API 명세서 바로가기](https://carnelian-cloudberry-496.notion.site/API-374ed7769383468b8f63b18f0f903609)
  
---
### 📒 Feedback

---

### 🐞 Bug Report
