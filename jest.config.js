module.exports = {
  testEnvironment: 'node',          // Node.js 环境测试
  collectCoverage: true,             // 收集覆盖率
  coverageDirectory: 'coverage',    // 覆盖率输出目录
  testMatch: [
    '**/__tests__/**/*.test.js',    // 测试文件匹配规则
    '**/?(*.)+(spec|test).js'
  ],
  // 如果你的代码用 ES Modules，可加下面两行：
  // transform: {},
  // extensionsToTreatAsEsm: ['.js'],

  // 如果你用 Babel 转译，可以在这里配置 transform
};

