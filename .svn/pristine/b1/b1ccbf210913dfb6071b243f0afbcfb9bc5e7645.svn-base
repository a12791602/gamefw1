import {REQUEST_PROMO} from '../constants/PromoConstant';
import {promoItemsUrl} from '../utils/url';

export function loadPromos() {
  return dispatch => {
    fetch(promoItemsUrl())
    .then(res => res.json())
    .then(json => {
      if (json.rs) {
        dispatch({
          type: REQUEST_PROMO,
          items: json.datas
        });
      }
    })
    .catch(err => {

    });
  };
}