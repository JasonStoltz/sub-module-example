requirejs.config({
  paths: {
    'jquery': '../lib/jquery/jquery'
  }
});

require(['core', 'common', 'temp', 'jquery'], function(core, common, temp, $){
  console.log("main loaded");
  core.loaded();
  common.loaded();
  temp.loaded();
  console.log($('div'));
});