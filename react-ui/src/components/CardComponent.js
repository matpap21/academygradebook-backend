import classes from './CardComponent.module.css';

const CardComponent = (props) => {
  return (
      <div className={classes.CardComponentContainer}>
          <div className={classes.CardTitle}>
              {props.title}
          </div>
          <div className={classes.CardContent}>
              {props.children}
          </div>
      </div>
  )
}
export default CardComponent;