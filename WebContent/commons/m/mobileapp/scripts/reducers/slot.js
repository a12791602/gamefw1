import * as SlotConstant from '../constants/SlotConstant';

const initState = {
  os: [],
  ttg: [],
  mg: [],
  pt: [],
  nt: [],
  res: {
    msg: '',
  },
  // 单个slot 对象, 比如在 like / not like slot 使用
  slots: {

  },
  flatStatus: {
    os: {
      status: false,
      res: {
        rs: true,
        msg: '',
      },
    },
    ttg: {
      status: false,
      res: {
        rs: true,
        msg: '',
      },
    },
    mg: {
      status: false,
      res: {
        rs: true,
        msg: '',
      },
    },
    pt: {
      status: false,
      res: {
        rs: true,
        msg: '',
      },
    },
    nt: {
      status: false,
      res: {
        rs: true,
        msg: '',
      },
    },
  },
};

export default function (state = initState, action) {

  switch (action.type) {
    case SlotConstant.SLOT_REQUEST:
      return Object.assign({}, state, action.slots);
    case SlotConstant.SLOT_REQUEST_ERROR:
      return Object.assign({}, state, {
        res: {
          msg: action.msg
        }
      });
    case SlotConstant.SLOT_CHECK_STATUS:
      let type = action.flat;
      let res = action.res;
      let flatStatus = initState.flatStatus;
      return Object.assign({}, state, {
        flatStatus: {
          [type]:  {
            status: !res.datas.status,
            res: {
              rs: res.rs,
              msg: res.datas.msg
            },
          }
        }
      });
  }

  return state;
} 