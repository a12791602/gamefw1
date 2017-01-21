import React, {PropTypes} from 'react';
import ReactDOM from 'react-dom';
import {detectCSSFeature} from '../utils/validate';

const FPS = 20;
const STEP = 1;
const TIMEOUT = 1 / FPS * 1000;

const Marquee = React.createClass({
  propTypes: {
    text: PropTypes.string,
    hoverToStop: PropTypes.bool,
    loop: PropTypes.bool,
    leading: PropTypes.number,
    trailing: PropTypes.number,
    className: PropTypes.string
  },

  getDefaultProps() {
    return {
      text: '',
      hoverToStop: false,
      loop: false,
      leading: 0,
      trailing: 0
    };
  },

  getInitialState() {
    return {
      animatedWidth: 0,
      overflowWidth: 0
    };
  },

  componentDidMount() {
    this.animateMounted = true;
    this._measureText();
    clearTimeout(this._marqueeTimer);

    if (this.props.hoverToStop) {
      this._startAnimation();
    }
  },

  componentDidUpdate() {
    this._measureText();

    if (this.props.hoverToStop) {
      this._startAnimation();
    }
  },

  componentWillUnmount() {
    this.animateMounted = false;
    clearTimeout(this._marqueeTimer);
  },

  handleMouseEnter() {
    if (this.props.hoverToStop) {
      clearTimeout(this._marqueeTimer);
    }
    else if (this.state.overflowWidth > 0){
      this._startAnimation();
    }
  },

  handleMouseLeave() {
    if (this.props.hoverToStop && this.state.overflowWidth > 0) {
      this._startAnimation();
    }
    else {
      clearTimeout(this._marqueeTimer);
      this.setState({
        animatedWidth: 0
      });
    }
  },

  render() {
    const style = {
      //'position': 'static',
      'position': 'relative',
      'display': 'inline-block',
      //'right': this.state.animatedWidth,
      'transform': `translate3d(-${this.state.animatedWidth}px, 0px, 0px)`,
      '-moz-transform': `translate3d(-${this.state.animatedWidth}px, 0px, 0px)`,
      '-ms-transform': `translate3d(-${this.state.animatedWidth}px, 0px, 0px)`,
      '-webkit-transform': `translate3d(-${this.state.animatedWidth}px, 0px, 0px)`,
      'whiteSpace': 'nowrap'
    };

    if (this.state.overflowWidth < 0) {
      return (
        <div className={`ui-marquee ${this.props.className}`} style={{overflow: 'hidden'}}>
          <span ref="text" style={style} title={this.props.text}>{this.props.text}</span>
        </div>
      );
    }
    else {
      return (
        <div className={`ui-marquee ${this.props.className}`} style={{overflow: 'hidden'}}
             onMouseEnter={this.handleMouseEnter}
             onMouseLeave={this.handleMouseLeave}>
          <span ref="text" style={style} title={this.props.text}>{this.props.text}</span>
        </div>
      );
    }
  },

  _startAnimation() {
    clearTimeout(this._marqueeTimer);
    const isLeading = this.state.animatedWidth === 0;
    const timeout = isLeading ? this.props.leading : TIMEOUT;

    const animate = () => {
      if (!this.animateMounted) {
        clearTimeout(this._marqueeTimer);
        return ;
      }
      const {overflowWidth} = this.state;
      let animatedWidth = this.state.animatedWidth + STEP;
      const isRoundOver = animatedWidth > overflowWidth;

      if (isRoundOver) {
        if (this.props.loop) {
          animatedWidth = 0;
        }
        else {
          return;
        }
      }

      if (isRoundOver && this.props.trailing) {
        this._marqueeTimer = setTimeout(() => {
          this.setState({
            animatedWidth
          });

          this._marqueeTimer = setTimeout(animate, TIMEOUT);
        }, this.props.trailing);
      }
      else {
        this.setState({
          animatedWidth
        });

        this._marqueeTimer = setTimeout(animate, TIMEOUT);
      }
    };

    this._marqueeTimer = setTimeout(animate, timeout);
  },

  _measureText() {
    const container = ReactDOM.findDOMNode(this);
    const node = ReactDOM.findDOMNode(this.refs.text);

    if (container && node) {
      const containerWidth = container.offsetWidth;
      const textWidth = node.offsetWidth;
      const overflowWidth = textWidth - containerWidth;

      if (overflowWidth !== this.state.overflowWidth) {
        this.setState({
          overflowWidth
        });
      }
    }
  }
});

export default Marquee;
