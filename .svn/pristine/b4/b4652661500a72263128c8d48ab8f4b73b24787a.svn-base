import * as LiveConstant from '../constants/LiveConstant';
import {checkFlatStatusUrl, options} from '../utils/url';

export function checkFlatStatus(flat) {
  return dispatch => {
    let form = new FormData();
    form.append('code', flat);
    fetch(checkFlatStatusUrl(), options({
      method: 'post',
      body: form
    }))
    .then(res => res.json())
    .then(json => {
      dispatch({
        type: LiveConstant.LIVE_CHECK_FLAT,
        res: json,
        flat
      });
    });
  }
}