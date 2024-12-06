<template>
  <div class="main">
    <el-container>
      <!-- 左侧导航栏 -->
      <el-aside width="250px" class="nav-container" :style="{ position: 'fixed', top: '0', left: '0', height: '100vh' }">
          <el-menu :default-active="defaultActive" style="min-height: 100%;" theme="dark" router>

            <el-menu-item index="" style="background-color: #c2c0c0;" class="custom-home-menu-item" disabled>
                  <i class="el-icon-menu"></i>
            </el-menu-item>
            <el-menu-item index="home" style="background-color: #c2c0c0;" class="custom-home-menu-item">
                  <i class="el-icon-menu"></i>首页
            </el-menu-item>
            <el-submenu index="2">
                  <template slot="title"><i class="el-icon-document"></i>SQL注入</template>
                  <el-menu-item index="SQL1">布尔注入</el-menu-item>
                  <el-menu-item index="SQL2">布尔盲注</el-menu-item>
                  <el-menu-item index="SQL3">延时盲注</el-menu-item>
              </el-submenu>
            <el-submenu index="3">
                  <template slot="title"><i class="el-icon-document"></i>命令注入</template>
                  <el-menu-item index="CMD1">命令执行_exec</el-menu-item>
                  <el-menu-item index="CMD2">命令执行_ProcessBuilder</el-menu-item>
                  <el-menu-item index="SpEL">SpEL表达式注入</el-menu-item>
                  <el-menu-item index="SSTI">SSTI模板注入</el-menu-item>
              </el-submenu>
        <el-submenu index="4">
                  <template slot="title"><i class="el-icon-document"></i>文件上传</template>
                  <el-menu-item index="upload">任意文件上传</el-menu-item>
                  <el-menu-item index="upload_white">白名单验证</el-menu-item>
              </el-submenu>
        <el-submenu index="5">
                  <template slot="title"><i class="el-icon-document"></i>文件读取</template>
                  <el-menu-item index="file_read">任意文件读取</el-menu-item>
              </el-submenu>
        <el-submenu index="6">
                  <template slot="title"><i class="el-icon-document"></i>SSRF</template>
                  <el-menu-item index="SSRF">SSRF</el-menu-item>
              </el-submenu>
        <el-submenu index="7">
                  <template slot="title"><i class="el-icon-document"></i>XXE</template>
                  <el-menu-item index="XXE">XXE</el-menu-item>
              </el-submenu>
        <el-submenu index="8">
                  <template slot="title"><i class="el-icon-document"></i>反序列化</template>
                  <el-menu-item index="Deserialize">反序列化</el-menu-item>
                  <el-menu-item index="fastjson">fastjson反序列化</el-menu-item>
                  <el-menu-item index="log4j">log4j反序列化</el-menu-item>
                  <el-menu-item index="shiro">shiro反序列化</el-menu-item>
              </el-submenu>
          </el-menu>
      </el-aside>
      <el-container>
        <!-- 顶部栏 -->
        <el-header class="header-container" :style="{ position: 'fixed', top: '0', left: '0', right: '0', zIndex: '10' }">
          <div class="header-left">
            <h2>蟹钳</h2>
          </div>
          <div class="header-right">
            <el-dropdown>
              <span class="el-dropdown-link">
                用户<i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>
                  <router-link :to="{ name: 're_pwd' }" class="custom-router-link" style="color: black; font-size: 13px;">修改密码</router-link>
                </el-dropdown-item>
                <el-dropdown-item>
                  <button @click="logout" style="border: none; background-color: transparent; padding: 0;">退出登录</button>
              </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </div>
        </el-header>
        <!-- 中间路由 -->
        <re_color>
        <router-view :style="{ marginLeft: '250px', marginTop: '60px' }"></router-view>
        </re_color>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  methods: {
    logout() {
      localStorage.removeItem('token');
      // 强制刷新页面
      location.reload();
    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
};
</script>

<style scoped>
.main {
  background-color: #ffffff;
  color: #d8d7d7;
}
.el-menu{
  background-color: #c2c0c0;
  border: none!important; /* 去掉边框 */
}
.el-menu-item{
  background-color: #3836367e;
}
.custom-home-menu-item {
    background-color: #c2c0c0;
}
.custom-home-menu-item:hover {
    background-color: #c2c0c0;
}
.header-container {
  background-color: #c2c0c0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  color: #ffffff;
}
.custom-router-link {
  text-decoration: none;
  color: inherit;
}


.header-left h2 {
  margin: 0;
}

.header-right.el-dropdown-link {
  color: white;
  cursor: pointer;
}

.nav-container {
  width: 200px; /* Set fixed width */
  background-color: #c2c0c0;
  padding: 10px;
  color: #324057;
}
</style>