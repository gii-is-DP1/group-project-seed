import { render, screen } from '../test-utils';
import {act} from '@testing-library/react'

import MatchList from './index';

describe("Match listing tests",()=>{
    test("Should show matches names", async ()=>{        
        act(() => {
            render(<MatchList />);
        });
        // Here we show the current state of the screen.
        screen.debug();
        
        
        const rows=await screen.findByText(/Parejo/);
        // We show the status again!
        screen.debug();
        await expect(rows).toBeInTheDocument();
        
    });
});