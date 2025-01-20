<template>
  <div class="form-container">
    <button @click="goBack" class="back-button">◁</button>

    <h2>회원가입</h2>

    <form @submit.prevent="handleSignup">
      <!-- 이메일 -->
      <div class="form-group">
        <label for="email">이메일</label>
        <div class="email-input-wrapper">
          <input 
            type="email" 
            id="email" 
            v-model="email" 
            placeholder="이메일 입력" 
            required 
          />
          <button type="button" @click="checkEmailDuplicate">중복 확인</button>
        </div>
        <span v-if="emailErrorMessage" style="color: red;">{{ emailErrorMessage }}</span>
      </div>

      <div class="form-group">
        <label for="verificationCode">인증번호</label>
        <div class="email-input-wrapper">
          <input 
            type="text" 
            id="verificationCode" 
            v-model="verificationCode" 
            placeholder="인증번호 입력" 
            required 
          />
          <button type="button" @click="sendVerificationCode">인증번호 받기</button>
          <button type="button" @click="verifyCode">인증하기</button>
        </div>
        <span v-if="verificationErrorMessage" style="color: red;">{{ verificationErrorMessage }}</span>
      </div>

      <!-- 비밀번호 -->
      <div class="form-group">
        <label for="password">비밀번호</label>
        <div class="password-input-wrapper">
          <input
            :type="passwordVisible ? 'text' : 'password'"
            id="password"
            v-model="password"
            placeholder="비밀번호 입력"
            required
          />
          <span class="toggle-visibility" @click="togglePasswordVisibility('password')">
            {{ passwordVisible ? '👁️' : '👁️‍🗨️' }}
          </span>
        </div>
      </div>
    
      <!-- 비밀번호 확인 -->
      <div class="form-group">
        <label for="passwordConfirm">비밀번호 확인</label>
        <div class="password-input-wrapper">
          <input
            :type="passwordConfirmVisible ? 'text' : 'password'"
            id="passwordConfirm"
            v-model="passwordConfirm"
            :class="{ 'input-error': isPasswordMismatch }"
            placeholder="비밀번호 확인 입력"
            required
          />
          <span class="toggle-visibility" @click="togglePasswordVisibility('passwordConfirm')">
            {{ passwordConfirmVisible ? '👁️' : '👁️‍🗨️' }}
          </span>
        </div>
        <span v-if="isPasswordMismatch" style="color: red;">비밀번호가 일치하지 않습니다.</span>
      </div>


      <!-- 이름 -->
      <div class="form-group">
        <label for="realname">이름</label>
        <input type="text" id="realname" v-model="realname" placeholder="이름 입력" required />
      </div>

      <!-- 닉네임 -->
      <div class="form-group">
        <label for="nickname">닉네임</label>
        <div class="email-input-wrapper">
          <input type="text" id="nickname" v-model="nickname" placeholder="닉네임 입력" required />
          <button type="button" @click="checkNicknameDuplicate">중복 확인</button>
        </div>
        <span v-if="nicknameErrorMessage" style="color: red;">{{ nicknameErrorMessage }}</span>
      </div>

      <!-- 생년월일 -->
      <div class="form-group">
        <label for="birthdate">생년월일</label>
        <input
          type="text"
          id="birthdate"
          v-model="birthdate"
          placeholder="YYYY-MM-DD"
          pattern="\d{4}-\d{2}-\d{2}"
          required
        />
      </div>

      <!-- 성별 -->
      <div class="form-group">
        <label>성별</label>
        <div class="gender">
          <label>
            <input type="radio" value="MALE" v-model="gender" required />
            남성
          </label>
          <label>
            <input type="radio" value="FEMALE" v-model="gender" required />
            여성
          </label>
        </div>
      </div>

      <!-- 선호 장르 -->
      <div class="form-group">
        <label>선호 장르(최소 1개 선택)</label>
        <div class="genre-select">
          <label for="genre1">-장르 1-</label>
          <select v-model="selectedGenre1" id="genre1" required>
            <option value="" disabled>선택</option>
            <option v-for="genre in allGenres" :key="genre.value" :value="genre.value">
              {{ genre.label }}
            </option>
          </select>
        </div>
        <div class="genre-select">
          <label for="genre2">-장르 2-</label>
          <select v-model="selectedGenre2" id="genre2">
            <option value="" disabled>선택</option>
            <option v-for="genre in allGenres" :key="genre.value" :value="genre.value">
              {{ genre.label }}
            </option>
          </select>
        </div>
        <div class="genre-select">
          <label for="genre3">-장르 3-</label>
          <select v-model="selectedGenre3" id="genre3">
            <option value="" disabled>선택</option>
            <option v-for="genre in allGenres" :key="genre.value" :value="genre.value">
              {{ genre.label }}
            </option>
          </select>
        </div>
      </div>
      <button type="submit" class="signup-button">회원가입하기</button>
    </form>
  </div>
</template>

<script>
import axios from "axios"; // axios import 추가

axios.defaults.baseURL = 'http://localhost:8081'; // Spring Boot 서버 주소

export default {
  name: "SignupView",
  data() {
    return {
      email: "",
      verificationCode: "",
      password: "",
      passwordConfirm: "",
      passwordVisible: false,
      passwordConfirmVisible: false,
      realname: "",
      nickname: "",
      birthdate: "",
      gender: "",
      allGenres: [
      { label: "판타지", value: "FANTASY" },
      { label: "과학 소설", value: "SCIENCE_FICTION" },
      { label: "미스터리", value: "MYSTERY" },
      ],
      selectedGenre1: "",
      selectedGenre2: "",
      selectedGenre3: "", 
      
      emailErrorMessage: "",
      verificationErrorMessage: "",
      isVerificationCodeSent: false,
    };
  },

  computed: {
    isPasswordMismatch() {
      return this.passwordConfirm && this.password !== this.passwordConfirm;
    },
  },

  methods: {
    goBack() {
      this.$router.push("/login");
    },
    
    async checkEmailDuplicate() {
    try {
        const response = await axios.get(`/api/user/check-email?email=${encodeURIComponent(this.email)}`);
        if (response.data.success) {
            this.emailErrorMessage = "사용 가능한 이메일입니다.";
        } else {
            /* this.emailErrorMessage = response.data.message; // ApiResponse에서 메시지 가져오기 */
            this.emailErrorMessage = "중복된 이메일입니다.";
        }
    } catch (error) {
        console.error("이메일 중복 확인 오류:", error);
    }
},

    async sendVerificationCode() {
      if (!this.email) {
        alert("이메일을 입력해주세요.");
        return;
      }
      try {
        const response = await axios.post("/api/email/send-verification", { email: this.email });
        if (response.data.isSuccess) {
          alert("인증번호가 이메일로 전송되었습니다.");
          this.isVerificationCodeSent = true;
        } else {
          alert("인증번호 전송에 실패했습니다.");
        }
      } catch (error) {
        console.error("인증번호 전송 오류:", error);
      }
    },

   async verifyCode() {
    if (!this.verificationCode) {
        alert("인증번호를 입력해주세요.");
        return;
    }
    try {
        const response = await axios.get(`/api/email/verify-code?inputCode=${this.verificationCode}`);
        if (response.data.isSuccess) {
            alert("인증번호가 확인되었습니다.");
            // 이후 회원가입 진행 또는 다음 단계로 이동
        } else {
            alert("인증번호 확인에 실패했습니다: " + response.data.message);
        }
    } catch (error) {
        console.error("인증번호 검증 오류:", error);
    }
  },

  async checkNicknameDuplicate() {
    try {
      const response = await axios.get(`/api/user/check-nickname?nickname=${encodeURIComponent(this.nickname)}`);
      if (response.data.success) {
        this.nicknameErrorMessage = "사용 가능한 닉네임입니다.";
      } else {
        this.nicknameErrorMessage = "중복된 닉네임입니다."; // API 메시지 사용
      }
    } catch (error) {
      console.error("닉네임 중복 확인 오류:", error);
    }
  },

  async handleSignup() {
  // 입력값 유효성 검증
  if (!this.email) {
    alert("이메일을 입력해주세요.");
    return;
  }

  if (this.emailErrorMessage !== "사용 가능한 이메일입니다.") {
    alert("이메일 중복 확인을 해주세요.");
    return;
  }

  if (!this.isVerificationCodeSent || !this.verificationCode) {
    alert("인증번호를 입력하고 인증을 완료해주세요.");
    return;
  }

  if (!this.password || !this.passwordConfirm) {
    alert("비밀번호를 입력해주세요.");
    return;
  }

  if (this.password !== this.passwordConfirm) {
    alert("비밀번호가 일치하지 않습니다.");
    return;
  }

  if (!this.realname) {
    alert("이름을 입력해주세요.");
    return;
  }

  if (!this.nickname) {
    alert("닉네임을 입력해주세요.");
    return;
  }

  if (this.nicknameErrorMessage !== "사용 가능한 닉네임입니다.") {
    alert("닉네임 중복 확인을 해주세요.");
    return;
  }

  if (!this.birthdate) {
    alert("생년월일을 입력해주세요.");
    return;
  }

  if (!this.gender) {
    alert("성별을 선택해주세요.");
    return;
  }

  if (!this.selectedGenre1) {
    alert("최소 한 개의 장르를 선택해주세요.");
    return;
  }

  // 회원가입 요청 데이터
  const signupData = {
    email: this.email,
    password: this.password,
    passwordConfirm: this.passwordConfirm,
    realname: this.realname,
    nickname: this.nickname,
    birthDate: this.birthdate,
    gender: this.gender,
    genre: [this.selectedGenre1, this.selectedGenre2, this.selectedGenre3].filter((g) => g),
  };

  // 서버 데이터 전송
  try {
    const response = await axios.post("/api/user/register", signupData);
    if (response.data.success) {
      alert("회원가입 성공!");
      this.$router.push("/login"); // 성공 시 로그인 페이지로 이동
    } else {
      alert("회원가입 실패: " + response.data.message);
    }
  } catch (error) {
    console.error("회원가입 오류:", error.response ? error.response.data : error.message);
    alert("회원가입 오류: " + (error.response ? error.response.data.message : "알 수 없는 오류입니다."));
  }
},


    togglePasswordVisibility(field) {
      if (field === "password") {
        this.passwordVisible = !this.passwordVisible;
      } else if (field === "passwordConfirm") {
        this.passwordConfirmVisible = !this.passwordConfirmVisible;
      }
    },
  },
};
</script>

  
<style scoped>
.form-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
}

.email-input-wrapper {
  display: flex;
  align-items: center;
  gap: 8px; /* 입력란과 버튼 사이의 간격 */
}

.email-input-wrapper input {
  flex: 1; /* 입력란이 가로로 확장되도록 설정 */
}

.email-input-wrapper button {
  padding: 8px 12px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background-color: #28a745;
  color: #fff;
  cursor: pointer;
  transition: background-color 0.3s;
}

.email-input-wrapper button:hover {
  background-color: #218838;
}

/* 비밀번호 스타일 추가 */
.password-input-wrapper {
  display: flex;
  align-items: center;
  position: relative;
}

.password-input-wrapper input {
  flex: 1;
}

.toggle-visibility {
  cursor: pointer;
  padding: 0 10px;
  font-size: 18px;
  color: #666;
  user-select: none;
}

input,
select {
  width: 100%;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

/* 성별 체크박스 스타일 수정 */
.gender {
  display: flex; /* 가로 배치 */
  gap: 100px; /* 항목 간 간격 */
}

.gender label {
  display: inline-flex; /* 라디오 버튼과 텍스트를 가로로 배치 */
  align-items: center; /* 수직 정렬 */
  gap: 5px; /* 라디오 버튼과 텍스트 간 간격 */
  margin: 0; /* 기본 마진 제거 */
  font-size: 10pt;
}

.signup-button {
  width: 100%;
  padding: 8px; /* 버튼 크기 줄이기 */
  font-size: 14px; /* 폰트 크기 줄이기 */
  color: #fff;
  background: #28a745;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.signup-button:hover {
  background: #218838;
}

.input-error {
  border: 2px solid red; /* 테두리를 빨간색으로 변경 */
  background-color: #ffe6e6; /* 옅은 빨간색 배경 */
}
</style>