import { themes } from 'prism-react-renderer'
import remarkMath from 'remark-math';
import rehypeKatex from 'rehype-katex';


module.exports = {
  title: 'Coisini',
  tagline: 'Developing with Coisini',
  url: 'https://doc.minddiy.top',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'throw',
  favicon: 'img/favicon.ico',
  organizationName: 'Liwen1234-cyber',
  deploymentBranch: 'gh-pages', // 部署的分支，默认 gh-pages
  projectName: 'mybook',
  trailingSlash: true,
  markdown: {
    format: 'detect',
    mermaid: true,
    preprocessor: ({filePath, fileContent}) => {
      return fileContent.replaceAll('{{MY_VAR}}', 'MY_VALUE');
    },
  },
  headTags: [
    {
      tagName: 'meta',
      attributes: {
        name: 'google-site-verification',
        content: '1FUPX6Qo4y3ecU623ShEurhgnjhSTjK49rRMhEDlzFA',
      },
    },
  ],
  themeConfig: {
    prism: {
      theme: themes.oneLight,
      darkTheme: themes.oneDark,
      additionalLanguages: ['java', 'python', 'cpp'],
    },
    navbar: {
      title: 'Coisini',
      logo: {
        alt: 'Chialisp Logo',
        src: 'img/logo.svg',
      },
      items: [
        {
          href: 'https://minddiy.top',
          label: 'Main site',
          position: 'right',
        },
      ],
    },
    mermaid: {
      theme: { light: 'neutral', dark: 'forest' }, // Optional theme settings for Mermaid
    },
  },
  presets: [
    [
      '@docusaurus/preset-classic',
      {
        theme: {
          customCss: require.resolve('./src/css/custom.css'),
        },
        docs: {
          routeBasePath: '/',
          sidebarPath: require.resolve('./sidebars.js'),
          remarkPlugins: [remarkMath],
          rehypePlugins: [rehypeKatex],
        },
      },
    ],
  ],
  themes: [
    [
      '@easyops-cn/docusaurus-search-local',
      /** @type {import("@easyops-cn/docusaurus-search-local").PluginOptions} */
      ({
        hashed: true,
        language: ['en', 'zh'],
        highlightSearchTermsOnTargetPage: true,
        explicitSearchResultPath: true,
        docsRouteBasePath: '/',
      }),
    ],
    '@docusaurus/theme-mermaid', // 正确添加 Mermaid 主题
  ],
  stylesheets: [
    {
      // href: 'https://cdn.jsdelivr.net/npm/katex@0.13.24/dist/katex.min.css',
      // type: 'text/css',
      // integrity:
      //   'sha384-odtC+0UGzzFL/6PNoE8rX/SPcQDXBJ+uRepguP4QkPCm2LBxH3FA3y+fKSiJ+AmM',
      // crossorigin: 'anonymous',
      href: '/katex/katex.min.css',
      type: 'text/css',
    },
  ],
  scripts: [
    {
      src: '/js/matomo.js',
      async: true,
      defer: true,
    },
  ],
};
