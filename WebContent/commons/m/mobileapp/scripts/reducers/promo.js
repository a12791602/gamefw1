import {REQUEST_PROMO} from '../constants/PromoConstant';

const initState = {
  items: []
};

export default function (state = initState, action) {
  switch (action.type) {
    case REQUEST_PROMO:
      return Object.assign({}, state, {
        items: action.items
      });
  }
  return state;
}