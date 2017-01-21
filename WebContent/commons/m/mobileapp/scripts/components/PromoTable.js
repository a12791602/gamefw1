import React, {Component, PropTypes} from 'react';
import {loadPromos} from '../actions/PromoAction';

class PromoTable extends Component {

  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const {dispatch} = this.props;
    dispatch(loadPromos());
  }

  render() {
    const {promo} = this.props;
    return (
      <div className="promo-table"><h3>优惠活动</h3>
      <ul className="promo-items items">
        {promo.items.map( item => {
          return (<li>
            <h4>{item.pmsTitle}</h4>
            <div dangerouslySetInnerHTML={{__html: item.pmsContent}} className="promo-content"></div>
          </li>);
        })}
      </ul></div>
    );
  }
};

PromoTable.propTypes = {
  promo: PropTypes.object.isRequired
};

export default PromoTable;