<template>
  <div class="SQL1">
    <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
      布尔注入
      <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
    </div>
    
    <el-container>
      <el-main class="main-container">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="id" label="id" width="180"></el-table-column>
          <el-table-column prop="name" label="name" width="180"></el-table-column>
          <el-table-column prop="username" label="username"></el-table-column>
        </el-table>
        
        <div class="input-button-container">
          <el-input v-model="inputValue" placeholder="id=" style="width: 120px;"></el-input>
          <el-button @click="fetchData" type="primary">查询</el-button>
        </div>
      </el-main>
    </el-container>

    <el-drawer :visible.sync="drawerVisible" direction="rtl" size="50%" title="Java代码示例">
      <div class="code-block">
        <h3>java源码</h3>
        <pre><code class="java">{{ javaCode }}</code></pre>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import hljs from 'highlight.js';
import 'highlight.js/styles/tomorrow-night.css';

export default {
  data() {
    return {
      tableData: [],
      inputValue: '',
      drawerVisible: false,
      javaCode: `
@GetMapping(value="/SQL1")
public User SQL_boolean(@RequestParam("id") String id){
        log.info("SQL注入参数：{}",id);

        User userid = null;
        try {
            userid = userService.SQL_injection(id);
        } catch (Exception e) {
            if (e instanceof org.springframework.dao.DataAccessException) {
                org.springframework.dao.DataAccessException dae = (org.springframework.dao.DataAccessException) e;
                return new User(null,  dae.getMessage(), null, null);
            }
        }
        return userid;
    }

UserMapper.java:
@Select("select * from user where ID = '\${ID}'")
User SQL_injection(String ID);
      `,
    };
  },
  methods: {
    fetchData() {
      const id = this.inputValue;
      this.$axios.get(`api/SQL1?id=${id}`)
        .then((result) => {
          this.tableData = [result.data];
        })
        .catch((error) => {
          console.error('获取数据出错：', error);
        });
    },
    highlightCode() {
      this.$nextTick(() => {
        const codeBlocks = document.querySelectorAll('pre code');
        codeBlocks.forEach((block) => {
          hljs.highlightBlock(block);
        });
      });
    }
  },
  watch: {
    drawerVisible(newVal) {
      if (newVal) {
        this.highlightCode(); // 当抽屉打开时进行高亮显示
      }
    }
  }
};
</script>

<style scoped>
.SQL1 {
  text-align: center;
}

.main-container {
  padding: 20px;
  background-color: #ffffff;
}

.input-button-container {
  display: flex;
  align-items: center;
  margin-top: 20px;
}

.code-block {
  margin-bottom: 20px;
  padding: 10px;
  background-color: #f4f4f4;
  border-radius: 8px;
}

pre {
  white-space: pre-wrap;
  word-wrap: break-word;
}

.ct-button {
  width: 120px;
  height: 40px;
  float: right;
}

.code-block {
  margin-left: 0;
  color: black;
  font-size: 16px;
  text-align: left;
}
</style>
