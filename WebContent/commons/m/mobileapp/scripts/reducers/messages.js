import * as MessageConstant from '../constants/MessageConstant';

const initState = [];

export default function (state = initState, action) {
  switch (action.type) {
    case MessageConstant.MESSAGE_REQUEST:
      let messages = action.messages.map(message => {
        return {title: message.ggContent}
      });
      return messages;
  }
  return state;
}