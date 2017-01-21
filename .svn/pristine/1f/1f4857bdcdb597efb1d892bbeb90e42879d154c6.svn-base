import * as MessageConstant from '../constants/MessageConstant';
import {messageUrl} from '../utils/url';

export function loadMessages() {
  return dispatch => {
    fetch(messageUrl())
    .then(res => res.json())
    .then(json => {
      dispatch({
        type: MessageConstant.MESSAGE_REQUEST,
        messages: json.datas
      });
    });
  };
}