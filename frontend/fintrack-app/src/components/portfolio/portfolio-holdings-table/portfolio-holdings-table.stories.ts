import type { Meta, StoryObj } from '@storybook/angular';
import { PortfolioHoldingsTable } from './portfolio-holdings-table.component';

const meta: Meta<PortfolioHoldingsTable> = {
  title: 'Components/PortfolioHoldingsTable',
  component: PortfolioHoldingsTable,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<PortfolioHoldingsTable>;

export const Primary: Story = {
  args: {},
};
