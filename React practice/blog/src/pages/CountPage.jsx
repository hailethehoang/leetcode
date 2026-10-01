import { useEffect, useRef } from "react";

const CountPage = ({ count }) => {
    // test useref
    const renderCount = useRef(0);
    renderCount.current += 1;
    console.log("Render:", renderCount.current);

    useEffect(() => {
        console.log('count.current', count)
        }
    )

    return (
        <>
            <h1>
                Component rendered {renderCount.current} times
            </h1>
            <h1>Hello you {count} times </h1>
        </>

    )
}
export default CountPage