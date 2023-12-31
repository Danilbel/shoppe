import { Link, NavLink } from 'react-router-dom';

import './header.scss';
import cartIcon from '../../assets/icons/cart.svg';
import profileIcon from '../../assets/icons/profile.svg';

const Header = ({ line }) => {
    return (
        <header className='header'>
            <div className='container'>
                <div className='header__row'>
                    <div className='header__logo'>
                        <Link to={'/'}>
                            S<span>HOPPE</span>
                        </Link>
                    </div>
                    <div className='header__wrapper'>
                        <nav className='header__nav'>
                            <ul>
                                <li>
                                    <NavLink
                                        to={'/shop'}
                                        className={({ isActive }) => [isActive ? 'header__link-active' : ''].join(' ')}>
                                        Shop
                                    </NavLink>
                                </li>
                                <li>
                                    <NavLink
                                        to={'/about'}
                                        className={({ isActive }) => [isActive ? 'header__link-active' : ''].join(' ')}>
                                        About
                                    </NavLink>
                                </li>
                            </ul>
                        </nav>
                        <div className='header__btns'>
                           
                            <NavLink
                                to={'/cart'}
                                className={({ isActive }) => [isActive ? 'header__link-active' : ''].join(' ')}>
                                <img src={cartIcon} alt='cart' />
                            </NavLink>
                            <NavLink
                                to={'/user'}
                                className={({ isActive }) => [isActive ? 'header__link-active' : ''].join(' ')}>
                                <img src={profileIcon} alt='profile' />
                            </NavLink>
                        </div>
                    </div>
                </div>
                {line ? <div className='header__line'></div> : null}
            </div>
        </header>
    );
};

export default Header;
