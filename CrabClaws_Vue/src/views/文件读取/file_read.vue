<template>
  <div class="file_read">
    <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
      任意文件读取
      <el-button @click="drawerVisible = true" type="primary" class="ct-button">查看Java源码</el-button>
    </div>
      <el-container>
          <!-- 中间内容 -->
          <el-main class="main-container">
              <!-- 表格 -->
              <el-table :data="tableData" style="width: 400%">
                  <el-table-column prop="msg" label="msg" width="150"></el-table-column>
                  <el-table-column prop="data" label="data" width="800"></el-table-column>
              </el-table>

              <!-- 文本框 -->
              <div class="input-button-container">
                  <el-input v-model="inputValue" placeholder="filename=" style="width: 120px;"></el-input>
                  <el-button @click="fetchData" type="primary">读取</el-button>
              </div>
          </el-main>
      </el-container>
    <el-drawer :visible.sync="drawerVisible" direction="rtl" size="60%" title="Java代码示例">
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
    @GetMapping("/fileread")
    public Result readFile(@RequestParam("filename") String filename) {
        try {
            // 假设文件在类路径下，这里使用ClassPathResource来获取文件资源
            String filePath = UPLOAD_DIR + "/" + filename;
            Resource resource = new FileSystemResource(filePath);
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine())!= null) {
                content.append(line).append("\\n");
            }
            reader.close();
            inputStream.close();
            // 返回包含文件内容的响应实体
            return Result.success(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
            // 如果读取文件出现错误，返回错误状态码和错误信息
            return Result.error("读取文件失败");
        }
    }
      `,
    };
  },
  methods: {
      fetchData() {
          const filename = this.inputValue;
          this.$axios.get(`api/files/fileread?filename=${filename}`)
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
.file_read_no {
  text-align: center;
}

.main-container {
  padding: 20px;
  background-color: #ffffff;
}

.input-container {
  margin-top: 20px;
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