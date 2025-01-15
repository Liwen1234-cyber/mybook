module.exports = {
  title: 'Coisini',
  tagline: 'Developing with Coisini',
  url: 'https://doc.middiy.top',
  baseUrl: '/',
  onBrokenLinks: 'throw',
  onBrokenMarkdownLinks: 'throw',
  favicon: 'img/favicon.ico',
  organizationName: 'Liwen1234-cyber',
  deploymentBranch: 'gh-pages', // 部署的分支，默认 gh-pages
  projectName: 'mybook',
  trailingSlash: true,
  themeConfig: {
    prism: {
      darkTheme: require('./src/theme/prism-dark-theme-chialisp'),
      theme: require('./src/theme/prism-light-theme-chialisp'),
    },
    navbar: {
      title: 'Coisini',
      logo: {
        alt: 'Chialisp Logo',
        src: 'img/logo.svg',
      },
      items: [
        {
          href: 'https://docs.chia.net',
          label: 'Chia Docs',
          position: 'left',
        },
        {
          href: 'https://docs.chia.net/academy-home/',
          label: 'Chia Academy',
          position: 'left',
        },
        {
          href: 'https://chia.net',
          label: 'Chia.net',
          position: 'right',
        },
        {
          href: 'https://github.com/Liwen1234-cyber/',
          label: 'GitHub',
          position: 'right',
        },

      ],
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
          editUrl:
            'https://github.com/Liwen1234-cyber/mybook/blob/main/',
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
  ],
  scripts: [
    {
      src: '/js/matomo.js',
      async: true,
      defer: true,
    },
  ],
};
