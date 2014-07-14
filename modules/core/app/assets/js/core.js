define(['common', 'jquery'], function(common, $) {

  common.loaded();

  return {
    loaded: function() {
      console.log('core loaded');
    }
  };
});