import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import BaseComponent from '../components/BaseComponent';

import TopBanner from '../components/TopBanner';
import HeaderNav from '../components/HeaderNav';
import ImageSlider from '../components/ImageSlider';
import Messages from '../components/Messages';
import LiveTypes from '../components/LiveTypes';
import PromoTable from '../components/PromoTable';
import Footer from '../components/Footer';

class PromoContainer extends BaseComponent {

  render() {
    return (
      <div className="page promo-page">
        <TopBanner {...this.props} />
        <HeaderNav />

        <PromoTable {...this.props}/>


        <Footer />
      </div>
    );
  }
}

export default connect(state => {
  const {user, promo} = state;
  return {user, promo};
})(PromoContainer);