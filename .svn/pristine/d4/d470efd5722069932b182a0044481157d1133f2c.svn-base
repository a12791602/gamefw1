export function password(password) {
  let r = /^[0-9A-z]{6,12}/;
  return r.test(password);
}

export function mobileNum(mobile) {
  return mobile.length == 11;
}

export function withdrawalPwd(pwd) {
  let r = /[0-9]{4}/;
  return r.test(pwd);
}

export function name(name) {
  let r = /^[0-9A-z\_\-]{4,10}/;
  return r.test(name);
}

export function detectCSSFeature(featurename){
    var feature = false,
    domPrefixes = 'Webkit Moz ms O'.split(' '),
    elm = document.createElement('div'),
    featurenameCapital = null;

    featurename = featurename.toLowerCase();

    if( elm.style[featurename] !== undefined ) { feature = true; } 

    if( feature === false ) {
        featurenameCapital = featurename.charAt(0).toUpperCase() + featurename.substr(1);
        for( var i = 0; i < domPrefixes.length; i++ ) {
            if( elm.style[domPrefixes[i] + featurenameCapital ] !== undefined ) {
              feature = true;
              break;
            }
        }
    }
    return feature; 
}