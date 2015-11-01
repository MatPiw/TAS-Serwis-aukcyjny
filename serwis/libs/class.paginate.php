<?php

/*
 * klasa wyświetlająca podstrony
 * param. wejściowe: $ile_produktów łącznie, $ile produktów na stronę, $jaki link przed podstronami ma się wyświetlać
 */


class paginate
{
    private $dirWww;
    private $sN;

    public function __construct($_dirWww = '')
    {
        if ( $_dirWww == '' )
            $this -> dirWww = dirWww;
        else
            $this -> dirWww = $_dirWww;
    }

    public function set($ile_prod, $na_strone, $link, $link2 = '', $_strona='', $_filters = '', $_filtersMore = '')
    {
        $u = (int)$ile_prod/$na_strone;

        if ($_strona > $u && $_strona > 1)
        {
            header('Location: '.dirWww.'404.html', 301 );
            exit();
        }

        if ($_strona >= 0)
            $this -> sN = $_strona+1;
        if ($na_strone >= $ile_prod)
            return false;

        if  ($na_strone == 0)
            return false;

        $i_podstron = $ile_prod / $na_strone;

        for ($i = 0; $i < $i_podstron; $i++)
        {
            $j = $i+1;
            $x = $j + ($_strona * 7 );
            //$u = $j * $na_strone;

            if ($i > ($_strona -7) && $i < ($_strona + 7))
                if ($j == ($this -> sN) )
                {
                    $subpages[$i]['href'] = $this -> dirWww.$link.';pN:'.$i.$_filtersMore.'/'.$link2.$_filters;
                    $subpages[$i]['text'] = $j;
                    $subpages[$i]['class'] = 'active';
                }
                else
                {
                    $subpages[$i]['href'] = $this -> dirWww.$link.';pN:'.$i.$_filtersMore.'/'.$link2.$_filters;
                    $subpages[$i]['text'] = $j;
                }
        }

        $mun = $_strona - 1;
        $num = $_strona + 1;

        if ($mun>=0)
        {
            $subpages['prev']['href'] = $this -> dirWww.$link.';pN:'.$mun.$_filtersMore.'/'.$link2.$_filters;
            $subpages['prev']['text'] = '<b class="arrow-left-blue-big"></b> Poprzednia strona';
        }

        if ($num<$i)
        {
            $subpages['next']['href'] = $this->dirWww . $link . ';pN:' . $num .$_filtersMore. '/' . $link2 . $_filters;
            $subpages['next']['text'] = 'Następna strona <b class="arrow-right-blue-big"></b>';
        }

        $_strona2 = $_strona+1;

        return $subpages;
    }
}
